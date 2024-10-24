package dev.akinbobobla.dailybot;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.users.UsersListRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DailyBot {
    private static final String token = System.getenv("SLACK_TOKEN");
    Slack slack = Slack.getInstance();
    MethodsClient methodsClient = slack.methods(token);

    public void sendMessage(String message) throws SlackApiException, IOException {
        ChatPostMessageRequest request = ChatPostMessageRequest
                .builder()
                .channel("U07T4EUHNVC")
                .text(message)
                .build();

        ChatPostMessageResponse response = methodsClient.chatPostMessage(request);

        if (response.isOk()) {
            System.out.println(response.getMessage());
        }
    }

    public List<String> getMembers() throws SlackApiException, IOException {
        UsersListRequest request = UsersListRequest
                .builder()
                .build();

        UsersListResponse response = methodsClient.usersList(request);

        if (response.isOk()) {
            List<String> responseData = response
                    .getMembers().stream().filter(member -> !member.isBot()).map(User::getId).toList();

            if (responseData.size() > 1) {
                return responseData.subList(1, responseData.size());
            } else {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }
}
