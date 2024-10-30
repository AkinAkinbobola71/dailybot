package dev.akinbobobla.dailybot.TeamMember;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String slackChannelName;

    private String slackId;
}
