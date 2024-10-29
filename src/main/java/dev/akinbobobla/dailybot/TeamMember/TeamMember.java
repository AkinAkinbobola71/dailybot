package dev.akinbobobla.dailybot.TeamMember;

import jakarta.persistence.*;
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

    @Column(unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> team;

    private String slackId;
}
