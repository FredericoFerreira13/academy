package com.ctw.workstation.rackAsset.entity;

import com.ctw.workstation.DTOs.Requests.RequestRackAssetDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackAssetDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface RackAssetMapper
{
    RackAssetMapper INSTANCE = Mappers.getMapper(RackAssetMapper.class);

    ResponseRackAssetDTO toDTO(RackAsset rackAsset);

    RackAsset toEntity(RequestRackAssetDTO rackAssetDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RackAsset updateEntity(RequestRackAssetDTO rackAssetDTO, @MappingTarget RackAsset updatedRackAsset);
}
