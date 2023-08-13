package com.example.bookmyshow.Services;

import com.example.bookmyshow.Dtos.RequestDto.AddUserDto;
import com.example.bookmyshow.Dtos.ResponseDto.UserResponseDto;
import com.example.bookmyshow.Exceptions.NoUserFoundException;
import com.example.bookmyshow.Models.User;
import com.example.bookmyshow.Repository.UserRepository;
import com.example.bookmyshow.Transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void addUser(AddUserDto userDto) {
        User user = UserTransformer.convertDtoToEntity(userDto);
        userRepository.save(user);
    }

    public UserResponseDto getOldestUserByAge() throws NoUserFoundException {
        // prevent you from exposing primary key
        // prevents from infinite recursion in case it occurs
        List<User> users = userRepository.findAll();
        Integer maxAge = 0;
        User UserAns = null;

        for(User user : users){
            if(user.getAge() > maxAge){
                maxAge = user.getAge();
                UserAns = user;
            }
        }
        if(UserAns == null) {
            throw new NoUserFoundException("No User Found");
        }

        // we need to transform the userEntity to the userResponse Dto
        UserResponseDto userResponseDto = UserTransformer.convertEntityToDto(UserAns);
        return userResponseDto;
    }

    public List<User> findUsersGreaterThanAAge(Integer age) {
        List<User> userList = userRepository.findUserWithAgeGreater(age);
        return userList;
    }
}
