package com.ctw.workstation.DTOs.Responses;

import java.util.Date;

public record ResponseBookingDTO(
        Long id,
        Long rackId,
        Long requesterId,
        Date bookFrom,
        Date bookTo,
        Date createdAt,
        Date modifiedAt
) {
}
