package com.application.springboot.repository;

import com.application.springboot.model.User;
import com.application.springboot.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {

    int countDistinctByLikedTo(User likedTo);

    List<Like> getDistinctByLikedTo(User likedTo);

    @Query("update Like l set l.status = :status  where l.id=:id")
    Like updatelikeStauts(@Param("id") int id,@Param("status") boolean status);

    int deleteById(int id);
}
