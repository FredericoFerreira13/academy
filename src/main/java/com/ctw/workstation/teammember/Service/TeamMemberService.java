package com.ctw.workstation.teammember.Service;

import com.ctw.workstation.DTOs.Requests.RequestTeamMemberDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamMemberDTO;
import com.ctw.workstation.Exceptions.ResourceNotFoundException;
import com.ctw.workstation.team.Service.TeamService;
import com.ctw.workstation.teammember.Repository.TeamMemberRepository;
import com.ctw.workstation.teammember.TeamMemberMapper;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TeamMemberService {

    @Inject
    TeamMemberRepository teamMemberRepository;

    @Inject
    TeamService teamService;

    @Inject
    TeamMemberMapper teamMemberMapper;

    @Inject
    Logger logger;

    public Optional<ResponseTeamMemberDTO> findTeamMemberById(Long id) {
        logger.infof("Finding team member by id %s", id);
        TeamMember t = teamMemberRepository.find("id", id).firstResult();
        return Optional.ofNullable(teamMemberMapper.toDTO(t));
    }

    public Optional<TeamMember> findTeamMember(Long id) {
        logger.infof("Finding team member by id %s", id);
        return Optional.ofNullable(teamMemberRepository.find("id", id).firstResult());
    }

    public ResponseTeamMemberDTO addTeamMember(RequestTeamMemberDTO teamMemberdto) {
        logger.infof("Adding team member to team with Id %s", teamMemberdto.teamId());
        Optional<ResponseTeamDTO> t = Optional.ofNullable(teamService.findTeamById(teamMemberdto.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + teamMemberdto.teamId() + " not found")));

        TeamMember tm = teamMemberMapper.toEntity(teamMemberdto);
        teamMemberRepository.persist(tm);
        return teamMemberMapper.toDTO(tm);
    }

    public List<ResponseTeamMemberDTO> getAllTeamMembers() {
        logger.infof("Getting all team members");
        return teamMemberRepository.streamAll().map(teamMemberMapper::toDTO).toList();
    }

    public boolean deleteTeamMember(Long id) {
        logger.infof("Deleting team member with id %s", id);
        return findTeamMember(id)
                .map(teamMember -> {
                            teamMemberRepository.deleteById(id);
                            return true;
                        }
                ).orElse(false);
    }

    public ResponseTeamMemberDTO updateTeamMember(Long id, RequestTeamMemberDTO newValuesTeamMemberDTO) {

        logger.infof("Updating team member with id %s", id);
        Optional<TeamMember> toUpdateTeamMember = Optional.ofNullable(findTeamMember(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team Member with ID " + id + " not found")));

        if (newValuesTeamMemberDTO.name() != null) {
            toUpdateTeamMember.get().name = newValuesTeamMemberDTO.name();
        }
        if (newValuesTeamMemberDTO.ctwId() != null) {
            toUpdateTeamMember.get().ctwId = newValuesTeamMemberDTO.ctwId();
        }
        if (newValuesTeamMemberDTO.createdAt() != null) {
            toUpdateTeamMember.get().createdAt = newValuesTeamMemberDTO.createdAt();
        }
        if (newValuesTeamMemberDTO.modifiedAt() != null) {
            toUpdateTeamMember.get().modifiedAt = newValuesTeamMemberDTO.modifiedAt();
        }
        if (newValuesTeamMemberDTO.teamId() != null) {
            Optional<ResponseTeamDTO> t = Optional.ofNullable(teamService.findTeamById(newValuesTeamMemberDTO.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + newValuesTeamMemberDTO.teamId() + " not found")));
            toUpdateTeamMember.get().teamId = newValuesTeamMemberDTO.teamId();
        }

        teamMemberRepository.persist(toUpdateTeamMember.get());
        return teamMemberMapper.toDTO(toUpdateTeamMember.get());

    }

    public Optional<ResponseTeamMemberDTO> newUpdateTeamMember(Long id, RequestTeamMemberDTO newValuesTeamMemberDTO) {

        logger.infof("Updating team with id %s", id);
        return findTeamMember(id)
                .map(teamMember -> {
                    if (newValuesTeamMemberDTO.teamId() != null) {
                        Optional<ResponseTeamDTO> t = Optional.ofNullable(teamService.findTeamById(newValuesTeamMemberDTO.teamId())
                                .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + newValuesTeamMemberDTO.teamId() + " not found")));
                    }

                    TeamMember updatedTeam = teamMemberMapper.updateEntity(newValuesTeamMemberDTO, teamMember);

                    teamMemberRepository.persist(updatedTeam);
                    return teamMemberMapper.toDTO(teamMember);
                });
    }

}


