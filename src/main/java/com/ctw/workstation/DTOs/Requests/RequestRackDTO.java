package com.ctw.workstation.DTOs.Requests;

import com.ctw.workstation.rack.entity.Status;

import java.sql.Timestamp;
import java.util.Date;

public record RequestRackDTO(
        Status status,
        String serialNumber,
        Long teamId,
        Date assembledAt,
        Date createdAt,
        Date modifiedAt,
        String defaultLocation
) {
}
