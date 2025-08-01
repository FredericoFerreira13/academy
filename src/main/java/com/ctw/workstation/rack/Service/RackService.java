package com.ctw.workstation.rack.Service;

import com.ctw.workstation.DTOs.Requests.RequestRackDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.Exceptions.ResourceNotFoundException;
import com.ctw.workstation.rack.RackMapper;
import com.ctw.workstation.rack.Repository.RackRepository;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.entity.Status;
import com.ctw.workstation.team.Service.TeamService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class RackService {

    @Inject
    RackRepository rackRepository;

    @Inject
    RackMapper rackMapper;

    @Inject
    TeamService teamService;

    public ResponseRackDTO addRack(RequestRackDTO rackDTO) {
        Optional<ResponseTeamDTO> t = Optional.ofNullable(teamService.findTeamById(rackDTO.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + rackDTO.teamId() + " not found")));

        Rack rack = rackMapper.toEntity(rackDTO);
        rackRepository.persist(rack);
        return rackMapper.toDTO(rack);
    }

    public List<ResponseRackDTO> getAllRacks() {
        return rackRepository.streamAll()
                .map(rackMapper::toDTO)
                .toList();
    }

    public Optional<ResponseRackDTO> findRackById(Long id) {
        Rack r = rackRepository.find("id", id).firstResult();
        return Optional.ofNullable(rackMapper.toDTO(r));
    }

    public List<ResponseRackDTO> getRacksByStatus(Status status) {
        return rackRepository.streamAll()
                .filter(s -> s.status.equals(status))
                .map(rackMapper::toDTO)
                .toList();
    }

    public Optional<Rack> findRack(Long id) {
        return Optional.ofNullable(rackRepository.find("id", id).firstResult());
    }

    public void deleteRack(Long id) {
        findRack(id)
                .map(rack -> {
                            rackRepository.deleteById(id);
                            return true;
                        }
                )
                .orElseThrow(() -> new ResourceNotFoundException("Rack with Id " + id + " not found"));

    }

    public ResponseRackDTO uppdateRack(Long id, RequestRackDTO newValuesRackDTO) {

        Optional<Rack> toUpdateTeam = Optional.ofNullable(findRack(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rack with ID " + id + " not found")));

        if (newValuesRackDTO.serialNumber() != null) {
            toUpdateTeam.get().serialNumber = newValuesRackDTO.serialNumber();
        }
        if (newValuesRackDTO.defaultLocation() != null) {
            toUpdateTeam.get().defaultLocation = newValuesRackDTO.defaultLocation();
        }
        if (newValuesRackDTO.createdAt() != null) {
            toUpdateTeam.get().createdAt = newValuesRackDTO.createdAt();
        }
        if (newValuesRackDTO.modifiedAt() != null) {
            toUpdateTeam.get().modifiedAt = newValuesRackDTO.modifiedAt();
        }
        if (newValuesRackDTO.defaultLocation() != null) {
            toUpdateTeam.get().defaultLocation = newValuesRackDTO.defaultLocation();
        }
        if (newValuesRackDTO.teamId() != null) {
            Optional<ResponseTeamDTO> t = Optional.ofNullable(teamService.findTeamById(newValuesRackDTO.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + newValuesRackDTO.teamId() + " not found")));
            toUpdateTeam.get().teamId = newValuesRackDTO.teamId();
        }

        rackRepository.persist(toUpdateTeam.get());
        return rackMapper.toDTO(toUpdateTeam.get());

    }

    public Optional<ResponseRackDTO> newUpdateRack(Long id, RequestRackDTO newValuesRackDTO) {

        return findRack(id)
                .map(rack -> {
                    if (newValuesRackDTO.teamId() != null) {
                        Optional<ResponseTeamDTO> t = Optional.ofNullable(teamService.findTeamById(newValuesRackDTO.teamId())
                                .orElseThrow(() -> new ResourceNotFoundException("Team with ID " + newValuesRackDTO.teamId() + " not found")));
                    }
                    Rack updatedRack = rackMapper.updateEntity(newValuesRackDTO, rack);
                    rackRepository.persist(updatedRack);
                    return rackMapper.toDTO(updatedRack);
                });
    }

}
