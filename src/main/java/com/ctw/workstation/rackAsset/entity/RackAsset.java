package com.ctw.workstation.rackAsset.entity;

import com.ctw.workstation.rack.entity.Rack;
import jakarta.persistence.*;

@Entity
@Table(name = "T_RACK_ASSET")
public class RackAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamIdGenerator")
    @SequenceGenerator(name = "teamIdGenerator", sequenceName = "SEQ_RACK_ASSET_ID")
    public Long id;

    @Column(name = "ASSET_TAG", nullable = false)
    public String asset_tag;

    @Column(name = "RACK_ID", nullable = false)
    public Long rackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RACK_ID", updatable = false, insertable = false, nullable = false)
    public Rack rack;

}
