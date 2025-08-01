package com.ctw.workstation.DTOs;

import java.util.Date;

public class BookingDTO {
    private String rackId;
    private String requesterId;
    private Date bookFrom;
    private Date bookTo;

    public String getRackId() {
        return rackId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public Date getBookFrom() {
        return bookFrom;
    }

    public Date getBookTo() {
        return bookTo;
    }
}

