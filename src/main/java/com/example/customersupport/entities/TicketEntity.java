package com.example.customersupport.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Tickets")
public class TicketEntity implements Serializable {

    private static final long serialVersionID = 1L;
    private long id;
    private String customerName;
    private String subject;
    private String bodyTicket;


@Id
    public long getId() {
        return id;
    }
@Column(name="id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public String getCustomerName() {
        return customerName;
    }


    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Basic
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    @Basic
    public String getBodyTicket() {
        return bodyTicket;
    }

    public void setBodyTicket(String bodyTicket) {
        this.bodyTicket = bodyTicket;
    }
}
