package com.ctw.workstation.booking;

import com.ctw.workstation.DTOs.Requests.RequestBookingDTO;
import com.ctw.workstation.DTOs.Responses.ResponseBookingDTO;
import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.rack.Service.RackService;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.teammember.Service.TeamMemberService;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Path("/workstation/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class BookingResource {

    private static final Logger logger = Logger.getLogger(BookingResource.class);

    @Inject
    BookingService bookingService;

    @POST
    public Response createBooking(RequestBookingDTO bookingdto) {
        ResponseBookingDTO created = bookingService.addBooking(bookingdto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    public Response getBookings() {
        List<ResponseBookingDTO> bookings = bookingService.getAllBookings();
        return  Response.status(Response.Status.OK).entity(bookings).build();
    }

    @GET
    @Path("{id}")
    public Response getBooking(@PathParam("id") Long id) {
        Optional<ResponseBookingDTO> booking = bookingService.findBookingById(id);
        return booking
                .map(bookingDto -> Response.ok(bookingDto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBooking(@PathParam("id") Long id) {
        bookingService.deleteBooking(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}")
    public Response updateBooking(@PathParam("id") Long id, RequestBookingDTO bookingdto) {
        Optional<ResponseBookingDTO> updatedBooking = bookingService.updateBooking(id,bookingdto);
        return updatedBooking
                .map(booking -> Response.ok(booking).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
        //return Response.status(Response.Status.OK).entity(updatedBooking).build();
    }

}