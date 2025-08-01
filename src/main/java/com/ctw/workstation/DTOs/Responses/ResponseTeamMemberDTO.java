package com.ctw.workstation.DTOs.Responses;

import java.util.Date;

public record ResponseTeamMemberDTO(
        Long id,
        Long teamId,
        String ctwId,
        String name,
        Date createdAt,
        Date modifiedAt
) {
}
