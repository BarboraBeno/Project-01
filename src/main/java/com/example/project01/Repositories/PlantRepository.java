package com.example.project01.Repositories;

import com.example.project01.Models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant,Long> {

    @Query("select p from Plant p where p.used = true")
    List<Plant> findAllByUsedIsTrue();

    @Query("select p from Plant p where p.used = false")
    List<Plant> findAllByUsedIsFalse();

    @Query("select p from Plant p order by p.strength DESC")
    List<Plant> findAllOrderByStrengthDesc();

    @Query("select p from Plant p where p.forKids = true")
    List<Plant> findAllByForKidsIsTrue();

    @Query("select p from Plant p where p.user.userId = :userId")
    List<Plant> findAllByUser_UserId(Long userId);

    @Query("select p from Plant p where p.user.userId IS null")
    List<Plant> findAllByUser_UserIdIsNull();
}
