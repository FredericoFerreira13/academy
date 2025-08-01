package com.ctw.workstation.rackAsset.entity;

import com.ctw.workstation.DTOs.Requests.RequestRackAssetDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackAssetDTO;
import com.ctw.workstation.rack.Service.RackService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.Optional;

@Path("/workstation/RackAssets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class RackAssetResource {

    @Inject
    RackAssetService rackAssetService;

    @POST
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400",description = "Bad request")
    public Response createRackAsset(@Valid RequestRackAssetDTO rackAssetDTO) {
        ResponseRackAssetDTO rackAsset = rackAssetService.addRackAsset(rackAssetDTO);
        return Response.status(Response.Status.CREATED).entity(rackAsset).build();
    }

    @GET
    public Response getRackAssets() {
        List<ResponseRackAssetDTO> rackAssets = rackAssetService.getAllRackAssets();
        return Response.status(Response.Status.OK).entity(rackAssets).build();
    }

    @GET
    @Path("{id}")
    public Response getRackAsset(@PathParam("id") Long id) {
        Optional<ResponseRackAssetDTO> rackAssetDTO = rackAssetService.findRackAssetByRackId(id);
        return rackAssetDTO
                .map(rackAsset -> Response.ok(rackAsset).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    public Response deleteRackAsset(@PathParam("id") Long id) {
        boolean deleted = rackAssetService.deleteRackAsset(id);
        return deleted
                ? Response.status(Response.Status.NO_CONTENT).build()
                : Response.status(Response.Status.NOT_FOUND).entity("Rack with ID " + id + " not found").build();
    }

    @PUT
    @Path("{id}")
    public Response updateRackAsset(@PathParam("id") Long id, RequestRackAssetDTO rackAssetDTO) {
        Optional<ResponseRackAssetDTO> updatedRackAsset= rackAssetService.updateRackAsset(id,rackAssetDTO);
        return updatedRackAsset
                .map(rackAsset -> Response.ok(rackAsset).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

}
