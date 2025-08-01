package com.ctw.workstation.rack.entity;

import com.ctw.workstation.DTOs.RackDTO;
import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.rackAsset.entity.RackAsset;
import com.ctw.workstation.team.entity.Team;
import io.quarkus.security.IdentityAttribute;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_RACK")
public class Rack extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackIdGenerator")
    @SequenceGenerator(name = "rackIdGenerator", sequenceName = "SEQ_RACK_ID")
    public Long id;
    @Column(name = "SERIAL_NUMBER", length = 20, nullable = false)
    public String serialNumber;
    @Transient
    public Integer age;
    @Temporal(TemporalType.DATE)
    @Column(name = "ASSEMBLED_AT")
    public Date assembledAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    public Status status;
    @Column(name = "TEAM_ID", nullable = false)
    public Long teamId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", updatable = false, insertable = false, nullable = false)
    public Team team;

    @OneToMany(mappedBy = "rack", fetch = FetchType.LAZY)
    public List<RackAsset> rackAssets;

    @OneToMany(mappedBy = "rackId", fetch = FetchType.LAZY)
    public List<Booking> booking;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.DATE)
    public Date createdAt;
    @Column(name = "MODIFIED_AT")
    @Temporal(TemporalType.DATE)
    public Date modifiedAt;

    @Column(name = "DEFAULT_LOCATION", nullable = false)
    public String defaultLocation;

}
