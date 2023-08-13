package com.example.bookmyshow.Repository;

import com.example.bookmyshow.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater,Integer> {

    Theater findByLocation(String location);
}
