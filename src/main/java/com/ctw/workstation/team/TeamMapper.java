package com.ctw.workstation.team;

import com.ctw.workstation.DTOs.Requests.RequestTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamWithMembersDTO;
import com.ctw.workstation.team.entity.Team;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    ResponseTeamDTO toDTO(Team team);

    Team toEntity(RequestTeamDTO teamDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team updateEntity(@MappingTarget Team team, RequestTeamDTO updatedTeam);

    ResponseTeamWithMembersDTO toDTOWithMembers(Team team);
}

