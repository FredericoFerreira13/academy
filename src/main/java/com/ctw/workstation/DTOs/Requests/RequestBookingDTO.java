package com.ctw.workstation.DTOs.Requests;

import java.util.Date;

public record RequestBookingDTO(
        Long rackId,
        Long requesterId,
        Date bookFrom,
        Date bookTo,
        Date createdAt,
        Date modifiedAt
) {
}
