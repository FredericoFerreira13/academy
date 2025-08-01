package com.ctw.workstation.booking;

import com.ctw.workstation.DTOs.Requests.RequestBookingDTO;
import com.ctw.workstation.DTOs.Responses.ResponseBookingDTO;
import com.ctw.workstation.booking.entity.Booking;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "cdi")
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    ResponseBookingDTO toDTO(Booking booking);

    Booking toEntity(RequestBookingDTO bookingDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Booking updateEntity(RequestBookingDTO bookingDTO, @MappingTarget Booking booking);
}
