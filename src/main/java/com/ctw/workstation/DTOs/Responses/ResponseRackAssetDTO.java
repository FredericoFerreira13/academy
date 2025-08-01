package com.ctw.workstation.DTOs.Responses;

public record ResponseRackAssetDTO(
        Long id,
        String asset_tag,
        Long rackId
) {
}
