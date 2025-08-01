package com.ctw.workstation.teammember;

import com.ctw.workstation.DTOs.Requests.RequestTeamMemberDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamMemberDTO;
import com.ctw.workstation.teammember.entity.TeamMember;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface TeamMemberMapper {

    TeamMemberMapper INSTANCE = Mappers.getMapper(TeamMemberMapper.class);

    ResponseTeamMemberDTO toDTO(TeamMember teamMember);

    TeamMember toEntity(RequestTeamMemberDTO teamDTO);

    TeamMember updateEntity(RequestTeamMemberDTO teamDTO, @MappingTarget TeamMember teamMember);
}
