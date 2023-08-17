package com.example.bookmyshow.Services;

import com.example.bookmyshow.Dtos.RequestDto.TicketRequestDto;
import com.example.bookmyshow.Dtos.ResponseDto.TicketResponseDto;
import com.example.bookmyshow.Exceptions.NoUserFoundException;
import com.example.bookmyshow.Exceptions.ShowNotFound;
import com.example.bookmyshow.Models.Show;
import com.example.bookmyshow.Models.ShowSeat;
import com.example.bookmyshow.Models.Ticket;
import com.example.bookmyshow.Models.User;
import com.example.bookmyshow.Repository.ShowRepository;
import com.example.bookmyshow.Repository.TicketRepository;
import com.example.bookmyshow.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    public TicketResponseDto bookTicket(TicketRequestDto ticketRequestDto) throws NoUserFoundException, ShowNotFound, Exception {
        int userId = ticketRequestDto.getUserId();

        Optional<User> userOptional = userRepository.findById(userId);

        if(!userOptional.isPresent()){
            throw new NoUserFoundException("User Id is incorrect");
        }

        int showId = ticketRequestDto.getShowId();
        Optional<Show> showOptional = showRepository.findById(showId);

        if(!showOptional.isPresent()){
            throw new ShowNotFound("Show is Not Found");
        }
        Show show = showOptional.get();

        // validation for the requested seats are available

        boolean  isValid = validRequestedAvailablity(show,ticketRequestDto.getRequestedSeats());

        if(isValid == false){
            throw new Exception("Requested seats entered are not available");

        }

        Ticket ticket = new Ticket();

        // calculate the total price
        int totalPrice = calculateTotalPrice(show,ticketRequestDto.getRequestedSeats());

        ticket.setTotalTicketsPrice(totalPrice);

        // convert the list of booked seats into string from list

        String bookedSeats = convertToString(ticketRequestDto.getRequestedSeats());
        ticket.setBookedSeats(bookedSeats);

        // do bidirectional mapping
        User user = userOptional.get();
        ticket = ticketRepository.save(ticket);

        user.getTicketList().add(ticket);
        // saving the relevant repository

        userRepository.save(user);

        show.getTicketList().add(ticket);

        showRepository.save(show);

        return createTicketResponseDto(show,ticket);

    }

    private TicketResponseDto createTicketResponseDto(Show show, Ticket ticket) {
        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .bookedSeats(ticket.getBookedSeats())
                .location(show.getTheater().getLocation())
                .theaterName(show.getTheater().getName())
                .movieName(show.getMovie().getMovieName())
                .showDate(show.getDate())
                .showTime(show.getTime()).build();

        return ticketResponseDto;
    }

    private boolean validRequestedAvailablity(Show show, List<String> requestedSeat){
        List<ShowSeat> showSeatList = show.getShowSeatList();
        for(ShowSeat showSeat : showSeatList){
            String seatNo = showSeat.getSeatNo();
            if(requestedSeat.contains(seatNo) && showSeat.isAvailable() == false){
                return false;
            }

        }
        return true;
    }

    private int calculateTotalPrice(Show show, List<String> requestedSeats){
        int totalPrice = 0;
        List<ShowSeat> showSeatList = show.getShowSeatList();
        for(ShowSeat showSeat : showSeatList){
            if(requestedSeats.contains(showSeat.getSeatNo())){
                totalPrice += showSeat.getPrice();

                showSeat.setAvailable(false);

            }
        }
        return totalPrice;
    }

    private String convertToString(List<String> seats){
        String result ="";
        for(String seatNo : seats){
            result = result + seatNo + ", ";
        }
        return result;
    }
}
