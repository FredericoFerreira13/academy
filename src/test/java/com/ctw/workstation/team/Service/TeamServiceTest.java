/**
package com.ctw.workstation.team.Service;

import com.ctw.workstation.DTOs.Requests.RequestTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.team.entity.Team;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TeamServiceTest {
    @Inject
    TeamService teamService;

    @Test
    @DisplayName("Create team")
    @Transactional
    void createTeam() {
        RequestTeamDTO teamDTO = new RequestTeamDTO
                ("Commodore Amiga",
                        "CTW Academy",
                        "Lisbon",
                        null, null
                );

        ResponseTeamDTO t = teamService.addTeam(teamDTO);

        Assertions.assertThat(t)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(teamDTO);

        Assertions.assertThat(teamService.findTeamById(t.id()))
                .isNotNull();

    }

}
 **/