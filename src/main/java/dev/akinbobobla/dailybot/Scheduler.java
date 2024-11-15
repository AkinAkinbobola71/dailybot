package dev.akinbobobla.dailybot;

import com.slack.api.bolt.App;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.MessageEvent;
import dev.akinbobobla.dailybot.TeamMember.TeamMember;
import dev.akinbobobla.dailybot.TeamMember.TeamMemberService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Scheduler {
    private final App app;

    private static final Map<String, List<String>> memberResponses = new HashMap<>();
    private static final Map<String, Integer> userQuestionState = new HashMap<>();
    private final TeamMemberService teamMemberService;

    private static final String[] questions = {
            "What did you do yesterday?",
            "What are you doing today?",
            "Do you have any blockers?"
    };

    public Scheduler(App app, TeamMemberService teamMemberService) {
        this.app = app;
        this.teamMemberService = teamMemberService;
    }

    public void schedule() {
        memberResponses.clear();
        userQuestionState.clear();

        List<String> finalMembers = teamMemberService.getTeamMembers();

        finalMembers.forEach(member -> startStandUp(app, member));

        app.event(MessageEvent.class, (req, ctx) -> {
            MessageEvent event = req.getEvent();
            String userId = event.getUser();

            if (event.getText() != null && finalMembers.contains(userId)) {
                saveResponse(userId, event.getText());

                if (memberResponses.get(userId).size() < questions.length) {
                    askQuestion(app, userId);
                } else {
                    printSummary(memberResponses.get(userId), userId, app);
                    memberResponses.get(userId).clear();
                    userQuestionState.put(userId, 0);
                }
            }
            return ctx.ack();
        });
    }

    private static void askQuestion(App app, String member) {
        try {
            int questionIndex = userQuestionState.get(member);

            if (questionIndex < questions.length) {
                app.client().chatPostMessage(r -> r.channel(member).text(questions[questionIndex]));
                userQuestionState.put(member, questionIndex + 1);
            }
        } catch (IOException | SlackApiException e) {
            throw new RuntimeException("Error sending message to user", e);
        }
    }

    private static void startStandUp(App app, String member) {
        userQuestionState.put(member, 0);
        memberResponses.put(member, new ArrayList<>());

        askQuestion(app, member);
    }

    private static void saveResponse(String member, String response) {
        memberResponses.get(member).add(response);
    }

    private void printSummary(List<String> responses, String userId, App app) throws SlackApiException, IOException {
        TeamMember teamMember = teamMemberService.getTeamMemberBySlackId(userId);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
        String formattedDate = LocalDateTime.now().format(dateTimeFormatter);

        StringBuilder messageContent = new StringBuilder();

        messageContent.append("*Daily Standup Summary for ")
                .append(formattedDate)
                .append("*\n\n")
                .append("User: ")
                .append("<@%s>".formatted(userId))
                .append("\n");


        for (int i = 0; i < questions.length; i++) {
            messageContent.append("\nQ: ")
                    .append(questions[i])
                    .append("\nA: ")
                    .append(responses.get(i))
                    .append("\n");
        }

        app.client().chatPostMessage(r -> r.channel(teamMember.getSlackChannelName()).text(messageContent.toString()));

//        app.client().chatPostMessage(r -> r.channel("#all-dailbot-clone").text(messageContent.toString()));
    }
}
