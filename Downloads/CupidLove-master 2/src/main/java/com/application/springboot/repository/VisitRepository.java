package com.application.springboot.repository;

import com.application.springboot.model.User;
import com.application.springboot.model.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visits,Integer> {

    List<Visits> getDistinctByVisitedUser(User user);
    int countDistinctByVisitedUser(User visitedUser);

}
