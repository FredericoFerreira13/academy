package com.ctw.workstation.rackAsset.Repository;

import com.ctw.workstation.rackAsset.entity.RackAsset;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RackAssetRepository implements PanacheRepository<RackAsset> {
}
