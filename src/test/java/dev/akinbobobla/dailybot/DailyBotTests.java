package dev.akinbobobla.dailybot;

import dev.akinbobobla.dailybot.TeamMember.TeamMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
public class DailyBotTests {
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private ExcelToDatabase excelToDatabase;

    @Test
    public void saveToDb() throws IOException {
        excelToDatabase.saveToDatabase();
    }
}
