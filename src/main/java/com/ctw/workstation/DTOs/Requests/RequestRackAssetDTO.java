package com.ctw.workstation.DTOs.Requests;

public record RequestRackAssetDTO(
        String asset_tag,
        Long rackId
) {
}
