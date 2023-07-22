package com.example.football.repository;


import com.example.football.models.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//ToDo:
public interface TownRepository  extends JpaRepository<Town, Long> {
    Town findByName(String name);
}
