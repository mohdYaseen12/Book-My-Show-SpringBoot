package com.example.bookmyshow.Services;

import com.example.bookmyshow.Dtos.RequestDto.AddShowDto;
import com.example.bookmyshow.Dtos.RequestDto.ShowSeatsDto;
import com.example.bookmyshow.Enums.SeatType;
import com.example.bookmyshow.Exceptions.MovieNotFound;
import com.example.bookmyshow.Exceptions.ShowNotFound;
import com.example.bookmyshow.Exceptions.TheaterNotFound;
import com.example.bookmyshow.Models.*;
import com.example.bookmyshow.Repository.MovieRepository;
import com.example.bookmyshow.Repository.ShowRepository;
import com.example.bookmyshow.Repository.TheaterRepository;
import com.example.bookmyshow.Transformers.ShowTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    public String addShow(AddShowDto addShowDto) throws TheaterNotFound, MovieNotFound {
        Show show = ShowTransformer.convertDtoToEntity(addShowDto);

        // set the movie and theater entity
        Optional<Movie> movieOptional = movieRepository.findById(addShowDto.getMovieId());

        if(!movieOptional.isPresent()){
            throw  new MovieNotFound("Movie is not found");
        }

        Optional<Theater> theaterOptional = theaterRepository.findById(addShowDto.getTheaterId());

        if(!theaterOptional.isPresent()){
            throw new TheaterNotFound("Theater not found");
        }

        Movie movie = movieOptional.get();
        Theater theater = theaterOptional.get();

        // setting the foreign key
        show.setMovie(movie);
        show.setTheater(theater);

        show = showRepository.save(show);

        movie.getShowList().add(show);
        movieRepository.save(movie);

        theater.getShowList().add(show);
        theaterRepository.save(theater);

        return "Show has been added and showId is" + show.getId();
    }


    public String associateShowSeats(ShowSeatsDto showSeatsDto) throws ShowNotFound {
        Optional<Show> optionalShow = showRepository.findById(showSeatsDto.getShowId());

        // Validation check
        if(!optionalShow.isPresent()){
            throw new ShowNotFound("Show Id is Incorrect");
        }

        Show show = optionalShow.get();

        Theater theater = show.getTheater();
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        List<ShowSeat> showSeatList = show.getShowSeatList();

        for(TheaterSeat theaterSeat : theaterSeatList){
            ShowSeat showSeat = new ShowSeat();

            showSeat.setSeatNo(theaterSeat.getSeatNo());
            showSeat.setSeatType(theaterSeat.getSeatType());

            if(showSeat.getSeatType().equals(SeatType.BASIC))
                showSeat.setPrice(showSeatsDto.getPriceForClassicSeats());
            else
                showSeat.setPrice(showSeatsDto.getPriceForPremiumSeats());

            // Foreign Key mapping
            showSeat.setShow(show);
            showSeat.setAvailable(true);
            showSeat.setFoodAttached(false);

            showSeatList.add(showSeat);

        }

        showRepository.save(show);
        // child will automatically get saved


        return "Show seats has been successfully added";


    }
}
