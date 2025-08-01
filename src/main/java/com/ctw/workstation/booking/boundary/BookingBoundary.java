package com.ctw.workstation.booking.boundary;

import com.ctw.workstation.DTOs.BookingDTO;
import com.ctw.workstation.booking.entity.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingBoundary {
/**
    private List<Booking> bookings = new ArrayList<>();

    public List<Booking> getBookings() {
        return bookings;
    }

    public Booking createBooking(Booking booking) {
        bookings.add(booking);
        return booking;
    }


    public void deleteBooking(String id) {
        bookings.removeIf(b -> b.getId().equals(id));
    }

    public Booking getBooking(String id) {
        return bookings.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    public Booking updateBooking(Booking bookingToUpdate, BookingDTO bookingDTO) {
        if (bookingToUpdate.getBookFrom() != null) {
            bookingToUpdate.setBookFrom(bookingDTO.getBookFrom());
        }
        if (bookingToUpdate.getBookTo() != null) {
            bookingToUpdate.setBookTo(bookingDTO.getBookTo());
        }
        if (bookingToUpdate.getRequesterId() != null) {
            bookingToUpdate.setRequesterId(bookingDTO.getRequesterId());
        }
        if (bookingToUpdate.getRackId() != null) {
       //     bookingToUpdate.setRackId(bookingDTO.getRackId());
        }
        return bookingToUpdate;
    }
    **/
}
