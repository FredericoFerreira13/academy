package com.ctw.workstation.rack;

import com.ctw.workstation.DTOs.Requests.RequestRackDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.rack.entity.Rack;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface RackMapper {

    RackMapper INSTANCE = Mappers.getMapper(RackMapper.class);

    ResponseRackDTO toDTO(Rack rack);

    Rack toEntity(RequestRackDTO rackDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Rack updateEntity(RequestRackDTO rackDTO, @MappingTarget Rack updatedRack);

}
