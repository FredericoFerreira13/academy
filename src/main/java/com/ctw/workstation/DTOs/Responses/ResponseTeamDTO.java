package com.ctw.workstation.DTOs.Responses;

//import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record ResponseTeamDTO (
        Long id,

        String name,

        String product,

        String defaultLocation,

        Date createdAt,

        Date modifiedAt){

}
