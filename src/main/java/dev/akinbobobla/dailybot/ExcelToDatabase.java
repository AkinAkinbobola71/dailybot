package dev.akinbobobla.dailybot;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.SlackApiException;
import dev.akinbobobla.dailybot.TeamMember.TeamMember;
import dev.akinbobobla.dailybot.TeamMember.TeamMemberService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ExcelToDatabase {
    private final TeamMemberService teamMemberService;
    private final App app;

    public ExcelToDatabase(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
        String botToken = System.getenv("SLACK_TOKEN");
        this.app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());
    }

    public void saveToDatabase() throws IOException {
        String fileLocation = "src/main/resources/static/Team Information.xlsx";
        Workbook workbook = WorkbookFactory.create(new File(fileLocation));

        workbook.forEach(sheet -> {
            DataFormatter dataFormatter = new DataFormatter();
            int index = 0;

            for (Row row : sheet) {
                TeamMember teamMember = new TeamMember();

                if (index++ == 0) {
                    continue;
                }

                if (row.getCell(4) != null && row.getCell(7) != null && row.getCell(2) != null && row.getCell(3) != null &&
                        !dataFormatter.formatCellValue(row.getCell(3)).contains("SQUAD") && dataFormatter.formatCellValue(row.getCell(7)).equals("#acquistion-retention")) {
                    try {
                        String email = dataFormatter.formatCellValue(row.getCell(4));
                        String slackId = null;

                        try {
                            var userResponse = app.client().usersLookupByEmail(r -> r.email(email));
                            if (userResponse.getUser() != null) {
                                slackId = userResponse.getUser().getId();
                            } else {
                                System.out.println("No Slack user found for email: " + email);
                            }
                        } catch (IOException | SlackApiException e) {
                            System.err.println("Error looking up Slack ID for email: " + email);
                        }

                        if (slackId != null) {
                            teamMember.setEmail(email);
                            teamMember.setSlackChannelName(dataFormatter.formatCellValue(row.getCell(7)));
                            teamMember.setSlackId(slackId);
                        } else {
                            System.out.println("No Slack ID found for email: " + email + ". User will not be added.");
                        }

                    } catch (Exception e) {
                        throw new RuntimeException("Unexpected error processing row", e);
                    }
                }


                if (teamMember.getEmail() != null && !teamMember.getEmail().isEmpty() &&
                        teamMember.getSlackChannelName() != null && !teamMember.getSlackChannelName().isEmpty() && teamMember.getSlackId() != null) {
                    teamMemberService.saveTeamMember(teamMember);
                }
            }
        });

        workbook.close();
    }
}
