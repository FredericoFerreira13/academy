package com.ctw.workstation.booking.entity;

import com.ctw.workstation.DTOs.BookingDTO;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "T_BOOKING")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingIdGenerator")
    @SequenceGenerator(name = "bookingIdGenerator", sequenceName = "SEQ_BOOKING_ID")
    public Long id;

    @Column(name = "RACK_ID", nullable = false)
    public Long rackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RACK_ID", updatable = false, insertable = false, nullable = false)
    public Rack rack;

    @Column(name = "REQUESTER_ID", nullable = false)
    public Long requesterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUESTER_ID",updatable = false, insertable = false, nullable = false)
    public TeamMember requester;

    @Temporal(TemporalType.DATE)
    @Column(name = "BOOK_FROM")
    public Date bookFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "BOOK_TO")
    public Date bookTo;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;
    @Column(name = "MODIFIED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date modifiedAt;

}
