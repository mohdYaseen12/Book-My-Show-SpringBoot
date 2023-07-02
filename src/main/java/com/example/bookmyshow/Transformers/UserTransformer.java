package com.example.bookmyshow.Transformers;

import com.example.bookmyshow.Dtos.RequestDto.AddUserDto;
import com.example.bookmyshow.Dtos.ResponseDto.UserResponseDto;
import com.example.bookmyshow.Models.User;

public class UserTransformer {
    public static User convertDtoToEntity(AddUserDto userDto){
//        User user = new User();
//        user.setName(userDto.getName());
//        user.setAge(userDto.getAge());
//        user.setMobileNo(userDto.getMobileNo());
//        user.setEmail(userDto.getEmail());

        // using @Builder annotation
        // works same as above
        User userObj = User.builder()
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .mobNo(userDto.getMobileNo())
                .build();

        return userObj;
    }

    public static UserResponseDto convertEntityToDto(User user){
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .age(user.getAge())
                .name(user.getName())
                .mobileNo(user.getMobNo())
                .build();
        return userResponseDto;
    }
}
