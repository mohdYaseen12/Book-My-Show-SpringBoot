package com.example.bookmyshow.Controller;

import com.example.bookmyshow.Dtos.RequestDto.AddUserDto;
import com.example.bookmyshow.Dtos.ResponseDto.UserResponseDto;
import com.example.bookmyshow.Models.User;
import com.example.bookmyshow.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public String addUser(@RequestBody AddUserDto userDto){
        userService.addUser(userDto);
        return "User Added successfully";
    }

    // get oldest user object by age
    @GetMapping("/getOldestUser")
    public UserResponseDto getOldestUserByAge(){
        try{
           UserResponseDto userResponseDto = userService.getOldestUserByAge();
           userResponseDto.setStatusCode("200");
           userResponseDto.setStatusMessage("Success");
           return userResponseDto;
        }
        catch (Exception e){
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setStatusCode("500");
            userResponseDto.setStatusMessage("Failure");

            return userResponseDto;
        }

    }
}
