package com.ctw.workstation.team.entity;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.teammember.entity.TeamMember;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_TEAM")
public class Team extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamIdGenerator")
    @SequenceGenerator(name = "teamIdGenerator", sequenceName = "SEQ_TEAM_ID")
    public Long id;

    @Column(name = "NAME", nullable = false)
    public String name;

    @OneToMany(mappedBy = "teamId", fetch = FetchType.LAZY)
    public List<Rack> racks;

    @Column(name = "PRODUCT", nullable = false)
    public String product;

    @OneToMany(mappedBy = "teamId", fetch = FetchType.LAZY)
    public List<TeamMember> teamMembers;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(name = "MODIFIED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date modifiedAt;

    @Column(name = "DEFAULT_LOCATION", nullable = false)
    public String defaultLocation;

}
