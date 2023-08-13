package com.example.bookmyshow.Services;


import com.example.bookmyshow.Dtos.RequestDto.TheaterEntryDto;
import com.example.bookmyshow.Dtos.RequestDto.TheaterSeatsEntryDto;
import com.example.bookmyshow.Enums.SeatType;
import com.example.bookmyshow.Models.Theater;
import com.example.bookmyshow.Models.TheaterSeat;
import com.example.bookmyshow.Repository.TheaterRepository;
import com.example.bookmyshow.Transformers.TheaterTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;

    public String addTheater(TheaterEntryDto theaterEntryDto){
        // Entity that saves into db
        // convert entryDto -> Entity and then save it
        Theater theater = TheaterTransformer.convertDtoToEntity(theaterEntryDto);
        theaterRepository.save(theater);
        return "Theater added successfully";
    }

    public String addTheaterSeats(TheaterSeatsEntryDto theaterSeatsEntryDto) {

        // we need to save the theaterSeat Entity
        int columns = theaterSeatsEntryDto.getNoOfSeatsIn1Row();
        int noOfClassicSeats = theaterSeatsEntryDto.getNoOfClassicSeats();
        int noOfPremiumSeats = theaterSeatsEntryDto.getNoOfPremiumSeats();

        String location = theaterSeatsEntryDto.getLocation();

        Theater theater = theaterRepository.findByLocation(location);
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();
        int counter = 1;
        char ch ='A';

    // this is done for the classic seats
        for(int count = 1; count <= noOfClassicSeats;count++){
            String seatNo = counter +"";
            seatNo = seatNo + ch;
            ch++;

            if((ch - 'A' + 1) == columns){
                ch = 'A';
                counter++;
            }
            TheaterSeat theaterSeat = new TheaterSeat();
            theaterSeat.setSeatNo(seatNo);
            theaterSeat.setTheater(theater);
            theaterSeat.setSeatType(SeatType.BASIC);

            // this is the bidirectional mapping.. storing the child entity in the parent entity
            theaterSeatList.add(theaterSeat);
        }

        /// for the premium seats
        for(int count = 1; count <= noOfPremiumSeats; count++){
            String seatNo = counter +"";
            seatNo = seatNo + ch;
            ch++;


            if((ch - 'A' + 1) == columns){
                ch = 'A';
                counter++;
            }
            TheaterSeat theaterSeat = new TheaterSeat();
            theaterSeat.setTheater(theater);
            theaterSeat.setSeatType(SeatType.PREMIUM);
            theaterSeat.setSeatNo(seatNo);

            // this is the bidirectional mapping.. storing the child entity in the parent entity
            theaterSeatList.add(theaterSeat);
        }

        // we need to save the parent : theater Entity
        // child will automatically get saved bcz of bidirectional mapping

        theaterRepository.save(theater);

        return "Theater Seats have been successfully added";

    }
}
