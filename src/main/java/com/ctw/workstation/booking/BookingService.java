package com.ctw.workstation.booking;

import com.ctw.workstation.DTOs.Requests.RequestBookingDTO;
import com.ctw.workstation.DTOs.Responses.ResponseBookingDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamMemberDTO;
import com.ctw.workstation.Exceptions.ResourceNotFoundException;
import com.ctw.workstation.booking.Repository.BookingRepository;
import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.rack.Service.RackService;
import com.ctw.workstation.teammember.Service.TeamMemberService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;


import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.persist;

@ApplicationScoped
public class BookingService {

    @Inject
    BookingRepository bookingRepository;

    @Inject
    BookingMapper bookingMapper;

    @Inject
    RackService  rackService;

    @Inject
    TeamMemberService teamMemberService;

    public Optional<ResponseBookingDTO> findBookingById(Long id) {
        Booking b = bookingRepository.find("id", id).firstResult();
        return Optional.ofNullable(bookingMapper.toDTO(b));
    }

    public Optional<Booking> findBooking(Long id) {
        return Optional.ofNullable(bookingRepository.find("id", id).firstResult());
    }

    public ResponseBookingDTO addBooking(RequestBookingDTO bookingDTO) {

        Optional<ResponseRackDTO> t = Optional.ofNullable(rackService.findRackById(bookingDTO.rackId())
                .orElseThrow(() -> new ResourceNotFoundException("Rack with ID " + bookingDTO.rackId() + " not found")));

        Optional<ResponseTeamMemberDTO> tm = Optional.ofNullable(teamMemberService.findTeamMemberById(bookingDTO.requesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Team Member with ID " + bookingDTO.requesterId() + " not found")));

        Booking b = bookingMapper.toEntity(bookingDTO);
        bookingRepository.persist(b);
        return bookingMapper.toDTO(b);
    }

    public List<ResponseBookingDTO> getAllBookings() {
        return bookingRepository.streamAll().map(bookingMapper::toDTO).toList();
    }

    public void deleteBooking(Long id) {
        findBooking(id)
                .map(booking -> {
                            bookingRepository.deleteById(id);
                            return true;
                        }
                )
                .orElseThrow(() -> new ResourceNotFoundException("Booking with Id " + id + " not found"));
    }

    public Optional<ResponseBookingDTO> updateBooking(Long id, RequestBookingDTO newValuesBookingDTO) {

        return findBooking(id)
                .map(booking -> {
                    if(newValuesBookingDTO.rackId() != null){
                        Optional<ResponseRackDTO> t = Optional.ofNullable(rackService.findRackById(newValuesBookingDTO.rackId())
                                .orElseThrow(() -> new ResourceNotFoundException("Rack with ID " + newValuesBookingDTO.rackId() + " not found")));
                    }

                    if(newValuesBookingDTO.requesterId() != null){
                        Optional<ResponseTeamMemberDTO> tm = Optional.ofNullable(teamMemberService.findTeamMemberById(newValuesBookingDTO.requesterId())
                                .orElseThrow(() -> new ResourceNotFoundException("Team Member with ID " + newValuesBookingDTO.requesterId() + " not found")));
                    }

                    Booking updateBooking = bookingMapper.updateEntity(newValuesBookingDTO,booking);
                    bookingRepository.persist(updateBooking);
                    return bookingMapper.toDTO(updateBooking);
                });
    }

}
