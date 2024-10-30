package dev.akinbobobla.dailybot;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
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

                if (row.getCell(4) != null && row.getCell(7) != null && row.getCell(2) != null && row.getCell(3) != null && !dataFormatter.formatCellValue(row.getCell(3)).contains("SQUAD")) {
                    teamMember.setEmail(dataFormatter.formatCellValue(row.getCell(4)));
                    teamMember.setSlackChannelName(dataFormatter.formatCellValue(row.getCell(7)));
                    teamMember.setFullName(dataFormatter.formatCellValue(row.getCell(2)) + " " + dataFormatter.formatCellValue(row.getCell(3)));
                }

                if (teamMember.getEmail() != null && !teamMember.getEmail().isEmpty() &&
                        teamMember.getSlackChannelName() != null && !teamMember.getSlackChannelName().isEmpty() &&
                        teamMember.getFullName() != null && !teamMember.getFullName().isEmpty()) {

                    teamMemberService.saveTeamMember(teamMember);
                }
            }
        });
    }
}
