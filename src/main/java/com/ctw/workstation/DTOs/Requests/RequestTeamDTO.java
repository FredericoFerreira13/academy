package com.ctw.workstation.DTOs.Requests;


import java.util.Date;

public record RequestTeamDTO(

        //@NotBlank(message="name may not be blank")
        String name,

      //  @NotBlank(message="product may not be blank")
        String product,

        String defaultLocation,

        Date createdAt,

        Date modifiedAt) {

}
