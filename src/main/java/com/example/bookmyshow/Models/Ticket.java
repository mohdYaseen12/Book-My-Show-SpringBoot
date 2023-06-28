package com.example.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ticket")
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int totalPrice;

    private String bookedSeat;

    private LocalTime time;

    private Date date;

    private String movieName;

    private String theaterName;


    // parent of showSeat class;
    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
    private List<ShowSeat> showSeatList = new ArrayList<>();


    //child class, it's parent class is User
    @ManyToOne
    @JoinColumn
    private User user;
}
