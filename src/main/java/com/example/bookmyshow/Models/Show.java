package com.example.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="show")
@Data
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalTime time;

    private Date date;


    //parent of theater
    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    private List<Theater> theaterList = new ArrayList<>();

    // parent of showSeat class
    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    private List<ShowSeat> showSeatList = new ArrayList<>();

}
