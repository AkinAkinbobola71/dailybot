package dev.akinbobobla.dailybot.TeamMembersResponse;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TeamMembersResponse {
    public boolean ok;
    public ArrayList<Member> members;
    public int cache_ts;
    public ResponseMetadata response_metadata;
}
