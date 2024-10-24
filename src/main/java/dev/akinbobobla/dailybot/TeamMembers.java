package dev.akinbobobla.dailybot;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TeamMembers {
    private static final String token = System.getenv("SLACK_TOKEN");
    private static final String apiUrl = "https://slack.com/api/users.list";

    public HttpResponse<String> getMembers() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .GET().build();

        HttpClient httpClient = HttpClient.newHttpClient();

        return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
    }
}
