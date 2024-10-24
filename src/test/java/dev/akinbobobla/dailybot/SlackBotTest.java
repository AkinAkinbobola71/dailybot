package dev.akinbobobla.dailybot;

import com.slack.api.methods.SlackApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class SlackBotTest {
    private DailyBot dailyBot;

    @BeforeEach
    public void setUp() {
        dailyBot = new DailyBot();
    }

    @Test
    public void testApi() throws SlackApiException, IOException {
//        dailyBot.sendMessage(":wave: Hi from a bot written in Java!");
        List<String> data = dailyBot.getMembers();
        System.out.println(data);
    }
}
