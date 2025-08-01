package com.ctw.workstation.teammember.entity;

import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.team.entity.Team;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_TEAM_MEMBER")
public class TeamMember extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamMemberIdGenerator")
    @SequenceGenerator(name = "teamMemberIdGenerator", sequenceName = "SEQ_TEAM_MEMBER_ID")
    public Long id;

    @Column(name = "TEAM_ID", nullable = false)
    public Long teamId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", updatable = false, insertable = false, nullable = false)
    public Team team;

    @Column(name = "CTW_ID", nullable = false)
    public String ctwId;

    @Column(name = "NAME", nullable = false)
    public String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    public Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFIED_AT")
    public Date modifiedAt;

    @OneToMany(mappedBy = "requesterId", fetch = FetchType.LAZY)
    public List<Booking> bookings;

}
