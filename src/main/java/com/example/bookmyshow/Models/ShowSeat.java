package com.example.bookmyshow.Models;

import com.example.bookmyshow.Enums.SeatType;
import jakarta.persistence.*;

@Entity
@Table(name="show_seats")
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seatNo;

    private int price;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private Boolean isAvailable;

    private Boolean isFoodAttached;


    // child class , it's parent class is show
    @ManyToOne
    @JoinColumn
    private Show show;

    // child class, it's Parent clas is Ticket
    @ManyToOne
    @JoinColumn
    private Ticket ticket;
}
