package com.ctw.workstation.rackAsset.entity;

import com.ctw.workstation.DTOs.Requests.RequestRackAssetDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackAssetDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.Exceptions.ResourceNotFoundException;
import com.ctw.workstation.rack.Repository.RackRepository;
import com.ctw.workstation.rack.Service.RackService;
import com.ctw.workstation.rackAsset.Repository.RackAssetRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class RackAssetService {

    @Inject
    RackAssetRepository rackAssetRepository;

    @Inject
    RackAssetMapper rackAssetMapper;

    @Inject
    RackService rackService;

    public Optional<ResponseRackAssetDTO> findRackAssetByRackId(Long rackId) {
        RackAsset rackAsset = rackAssetRepository.find("id", rackId).firstResult();
        return Optional.ofNullable(rackAssetMapper.toDTO(rackAsset));
    }

    public Optional<RackAsset> findRackAsset(Long id) {
        return Optional.ofNullable(rackAssetRepository.find("id", id).firstResult());
    }

    public ResponseRackAssetDTO addRackAsset(RequestRackAssetDTO rackAssetDTO) {
        Optional<ResponseRackDTO> t = Optional.ofNullable(rackService.findRackById(rackAssetDTO.rackId())
                .orElseThrow(() -> new ResourceNotFoundException("Rack with ID " + rackAssetDTO.rackId() + " not found")));

        RackAsset r = rackAssetMapper.toEntity(rackAssetDTO);
        rackAssetRepository.persist(r);
        return rackAssetMapper.toDTO(r);
    }

    public List<ResponseRackAssetDTO> getAllRackAssets() {
        return rackAssetRepository.streamAll().map(rackAssetMapper::toDTO).toList();
    }

    public ResponseRackAssetDTO updateRackAsset(RequestRackAssetDTO newValuesRackAssetDTO, RackAsset toUpdateRackAsset) {

        if (newValuesRackAssetDTO.rackId() != null) {
            toUpdateRackAsset.rackId = newValuesRackAssetDTO.rackId();
        }
        if (newValuesRackAssetDTO.asset_tag() != null) {
            toUpdateRackAsset.asset_tag = newValuesRackAssetDTO.asset_tag();
        }

        rackAssetRepository.persist(toUpdateRackAsset);
        return rackAssetMapper.toDTO(toUpdateRackAsset);

    }

    public boolean deleteRackAsset(Long id) {
        return findRackAssetByRackId(id)
                .map(rackAssetDTO -> {
                            rackAssetRepository.deleteById(id);
                            return true;
                        }
                ).orElse(false);
    }

    public Optional<ResponseRackAssetDTO> updateRackAsset(Long id, RequestRackAssetDTO newValuesRackAssetDTO) {
        return findRackAsset(id)
                .map(rackAsset -> {
                    if (newValuesRackAssetDTO.rackId() != null) {
                        Optional<ResponseRackDTO> r = Optional.ofNullable(rackService.findRackById(newValuesRackAssetDTO.rackId())
                                .orElseThrow(() -> new ResourceNotFoundException("Rack with ID " + newValuesRackAssetDTO.rackId() + " not found")));
                    }
                    RackAsset r = rackAssetMapper.updateEntity(newValuesRackAssetDTO, rackAsset);
                    rackAssetRepository.persist(r);
                    return rackAssetMapper.toDTO(r);
                });
    }

}
