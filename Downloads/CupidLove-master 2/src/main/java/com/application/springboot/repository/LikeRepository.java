package com.application.springboot.repository;

import com.application.springboot.model.User;
import com.application.springboot.model.like.Like;
import com.application.springboot.service.LikeService.LikeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {


    Like countDistinctByLikedTo(User likedTo);

    List<Like>  getDistinctByLikedTo(User likedTo);

}
