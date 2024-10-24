package dev.akinbobobla.dailybot;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@Getter
@Setter
public class SlackBot {
    private static final String token = System.getenv("SLACK_TOKEN");
    private static final String apiUrl = "https://slack.com/api/chat.postMessage";
    private List<String> membersIds;
    PostBody postBody = new PostBody();
    Gson gson = new Gson();


    public void sendMessage(String message) throws URISyntaxException, IOException, InterruptedException {
        postBody.setChannel("U07T4EUHNVC");
        postBody.setText(message);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(apiUrl))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(postBody)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
