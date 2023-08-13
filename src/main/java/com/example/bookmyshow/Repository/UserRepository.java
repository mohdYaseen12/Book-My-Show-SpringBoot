package com.example.bookmyshow.Repository;

import com.example.bookmyshow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "select * from users where age >= :value ;",nativeQuery = true)
     List<User> findUserWithAgeGreater(@Param("value")Integer enteredAge);
//    This is a custom function which we have defined
//   we have to write @Query on top of this.
//  to use MySql command / script always use nativeQuery=true
// to use parameter use semicolon(:) before parameter then exact name of parameter.
}
