package com.ctw.workstation.DTOs.Responses;

import com.ctw.workstation.teammember.entity.TeamMember;

import java.util.Date;
import java.util.List;

public record ResponseTeamWithMembersDTO(
        Long id,

        String name,

        String product,

        String defaultLocation,

        Date createdAt,

        Date modifiedAt,

        List<ResponseTeamMemberDTO> members
) {
}
