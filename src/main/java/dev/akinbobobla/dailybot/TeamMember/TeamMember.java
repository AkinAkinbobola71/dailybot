package dev.akinbobobla.dailybot.TeamMember;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class TeamMember {
    @Id
    @GeneratedValue
    private Integer id;

    private String fullName;

    private String email;

    @ElementCollection
    private List<String> team;

    private String slackId;

}
