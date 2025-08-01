package com.ctw.workstation.DTOs.Responses;

import java.util.Date;

public record ResponseRackDTO(
        Long id,
        String status,
        String serialNumber,
        String teamId,
        Date assembledAt,
        Date createdAt,
        Date modifiedAt,
        String defaultLocation) {
}
