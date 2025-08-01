package com.ctw.workstation.team.Service;

import com.ctw.workstation.DTOs.Requests.RequestTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamWithMembersDTO;
import com.ctw.workstation.team.Repository.TeamRepository;
import com.ctw.workstation.team.TeamMapper;
import com.ctw.workstation.team.entity.Team;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TeamService {

    @Inject
    TeamRepository teamRepository;

    @Inject
    TeamMapper teamMapper;

    @Inject
    Logger logger;

    public Optional<ResponseTeamDTO> findTeamById(Long id) {
        logger.infof("Finding team by id %s", id);
        Team t = teamRepository.find("id", id).firstResult();
        return Optional.ofNullable(teamMapper.toDTO(t));
    }

    public Optional<Team> findTeam(Long id) {
        logger.infof("Finding team by id %s", id);
        return Optional.ofNullable(teamRepository.find("id", id).firstResult());
    }

    public ResponseTeamDTO addTeam(RequestTeamDTO teamdto) {
        logger.info("Adding team");
        Team t = teamMapper.toEntity(teamdto);
        teamRepository.persist(t);
        ResponseTeamDTO responseTeamDTO = teamMapper.toDTO(t);
        return responseTeamDTO;
    }

    public List<ResponseTeamDTO> getAllTeams() {
        logger.info("Getting all teams");
        return teamRepository.streamAll().map(teamMapper::toDTO).toList();
    }

    public List<ResponseTeamWithMembersDTO> getAllTeamsWithMembers() {
        logger.info("Getting all teams");
        return teamRepository.findAllWithTeamMembers().map(teamMapper::toDTOWithMembers).toList();
    }

    public boolean deleteTeam(Long id) {
        logger.infof("Deleting team with id %s", id);
        return findTeamById(id)
                .map(team -> {
                            teamRepository.deleteById(id);
                            return true;
                        }
                ).orElse(false);
    }

    private Team setUpdatedValues(Team toUpdateTeam, RequestTeamDTO newValuesTeamDTO) {
        if (newValuesTeamDTO.name() != null) {
            toUpdateTeam.name = newValuesTeamDTO.name();
        }
        if (newValuesTeamDTO.product() != null) {
            toUpdateTeam.product = newValuesTeamDTO.product();
        }
        if (newValuesTeamDTO.createdAt() != null) {
            toUpdateTeam.createdAt = newValuesTeamDTO.createdAt();
        }
        if (newValuesTeamDTO.modifiedAt() != null) {
            toUpdateTeam.modifiedAt = newValuesTeamDTO.modifiedAt();
        }
        if (newValuesTeamDTO.defaultLocation() != null) {
            toUpdateTeam.defaultLocation = newValuesTeamDTO.defaultLocation();
        }
        return toUpdateTeam;
    }

    public Optional<ResponseTeamDTO> newUpdateTeam(Long id, RequestTeamDTO newValuesTeamDTO) {
        logger.infof("Updating team with id %s", id);
        return findTeam(id)
                .map(team -> {
                    Team updatedTeam = teamMapper.updateEntity(team, newValuesTeamDTO);
                    teamRepository.persist(updatedTeam);
                    return teamMapper.toDTO(team);
                });
    }

}
