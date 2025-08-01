package com.ctw.workstation.teammember;

import com.ctw.workstation.DTOs.Requests.RequestTeamMemberDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamMemberDTO;
import com.ctw.workstation.teammember.Service.TeamMemberService;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.Optional;

@Path("/workstation/teamMembers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class TeamMemberResource {

    @Inject
    TeamMemberService  teamMemberService;

    @POST
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400",description = "Bad request")
    public Response createTeamMember(@Valid RequestTeamMemberDTO teamMemberdto) {
        ResponseTeamMemberDTO tm = teamMemberService.addTeamMember(teamMemberdto);
        return Response.status(Response.Status.CREATED).entity(tm).build();
    }

    @GET
    public Response getTeamMembers() {
        List<ResponseTeamMemberDTO> teams = teamMemberService.getAllTeamMembers();
        return Response.status(Response.Status.OK).entity(teams).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTeamMember(@PathParam("id") Long id) {
        boolean deleted = teamMemberService.deleteTeamMember(id);
        return deleted
                ? Response.status(Response.Status.NO_CONTENT).build()
                : Response.status(Response.Status.NOT_FOUND).entity("Team Member with ID " + id + " not found").build();
    }

    @PUT
    @Path("{id}")
    public Response updateTeamMember(@PathParam("id") Long id, RequestTeamMemberDTO teamdto) {
        Optional<ResponseTeamMemberDTO> updatedTeamMember = teamMemberService.newUpdateTeamMember(id, teamdto);
        return  updatedTeamMember
                .map(teamMemberDTO -> Response.ok(teamMemberDTO).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("{id}")
    public Response getTeamMember(@PathParam("id") Long id) {
        Optional<ResponseTeamMemberDTO> teamMember = teamMemberService.findTeamMemberById(id);
        return teamMember
                .map(teamMemberDTO -> Response.ok(teamMemberDTO).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

}
