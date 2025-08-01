package com.ctw.workstation.DTOs.Requests;

import java.util.Date;

public record RequestTeamMemberDTO(
        Long teamId,
        String ctwId,
        String name,
        Date createdAt,
        Date modifiedAt
) {
}
