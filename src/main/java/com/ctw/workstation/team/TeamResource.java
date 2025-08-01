package com.ctw.workstation.team;

import com.ctw.workstation.DTOs.Requests.RequestTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamWithMembersDTO;
import com.ctw.workstation.team.Service.TeamService;
import com.ctw.workstation.team.entity.Team;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.Optional;

@Path("/workstation/teams")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class TeamResource {

    @Inject
    TeamService teamService;

    @POST
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad request")
    public Response createTeam(@Valid RequestTeamDTO teamdto) {
        ResponseTeamDTO t = teamService.addTeam(teamdto);
        return Response.status(Response.Status.CREATED).entity(t).build();
    }

    @GET
    public Response getTeams() {
        //List<ResponseTeamDTO> teams = teamService.getAllTeams();
        List<ResponseTeamWithMembersDTO> teams = teamService.getAllTeamsWithMembers();
        return Response.status(Response.Status.OK).entity(teams).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTeam(@PathParam("id") Long id) {
        boolean deleted = teamService.deleteTeam(id);
        return deleted
                ? Response.status(Response.Status.NO_CONTENT).build()
                : Response.status(Response.Status.NOT_FOUND).entity("Team with ID " + id + " not found").build();

    }

    @PUT
    @Path("{id}")
    public Response updateTeam(@PathParam("id") Long id, RequestTeamDTO teamdto) {
        Optional<ResponseTeamDTO> updatedTeam = teamService.newUpdateTeam(id, teamdto);
        return updatedTeam
                .map(teamDTO -> Response.ok(teamDTO).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("{id}")
    public Response getTeam(@PathParam("id") Long id) {
        Optional<ResponseTeamDTO> team = teamService.findTeamById(id);

        return team
                .map(teamDTO -> Response.ok(teamDTO).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

}
