package com.example.bookmyshow.Controller;

import com.example.bookmyshow.Dtos.RequestDto.TheaterEntryDto;
import com.example.bookmyshow.Dtos.RequestDto.TheaterSeatsEntryDto;
import com.example.bookmyshow.Services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theater")
public class TheaterController {

    @Autowired
    TheaterService theaterService;
    @PostMapping("/add")
    public String addTheater(@RequestBody TheaterEntryDto theaterEntryDto){
            return theaterService.addTheater(theaterEntryDto);
    }

    @PostMapping("/addTheaterSeats")
    public String addTheaterSeats(@RequestBody TheaterSeatsEntryDto theaterSeatsEntryDto){
        return theaterService.addTheaterSeats(theaterSeatsEntryDto);
    }
}
