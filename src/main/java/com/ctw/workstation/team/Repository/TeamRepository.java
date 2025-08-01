package com.ctw.workstation.team.Repository;

import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.entity.TeamMember;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Stream;


@ApplicationScoped
public class TeamRepository implements PanacheRepository<Team> {

    public Stream<Team> findAllWithTeamMembers() {
        return find("SELECT DISTINCT t from Team t LEFT JOIN FETCH t.teamMembers").stream();
    }

}
