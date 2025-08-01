package com.ctw.workstation.rack;

import com.ctw.workstation.DTOs.Requests.RequestRackDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.rack.Service.RackService;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.entity.Status;
import com.ctw.workstation.team.Service.TeamService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.Optional;

@Path("/workstation/racks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class RackResource {

    @Inject
    RackService rackService;

    @Inject
    TeamService teamService;

    @POST
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad request")
    public Response createRack(@RequestBody RequestRackDTO rackdto) {
        ResponseRackDTO created = rackService.addRack(rackdto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    public Response getRacks(@QueryParam("status") Status status) {

        if (status != null) {
            List<ResponseRackDTO> racksByStatus = rackService.getRacksByStatus(status);
            return Response.status(Response.Status.OK).entity(racksByStatus).build();
        }

        List<ResponseRackDTO> racks = rackService.getAllRacks();
        return Response.status(Response.Status.OK).entity(racks).build();
    }

    @GET
    @Path("{id}")
    public Response getRack(@PathParam("id") Long id) {
        Optional<ResponseRackDTO> rack = rackService.findRackById(id);

        return rack
                .map(rackDto -> Response.ok(rackDto).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("{id}")
    public Response updateRack(@PathParam("id") Long id, RequestRackDTO rackdto) {
        Optional<ResponseRackDTO> updatedRack = rackService.newUpdateRack(id, rackdto);
        return updatedRack
                .map(rackDTO -> Response.ok(rackDTO).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
        //return Response.status(Response.Status.OK).entity(updatedRack).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteRack(@PathParam("id") Long id) {
        rackService.deleteRack(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}

