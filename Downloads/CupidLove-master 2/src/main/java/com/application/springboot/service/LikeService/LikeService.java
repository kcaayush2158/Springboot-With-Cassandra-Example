package com.application.springboot.service.LikeService;

import com.application.springboot.model.User;
import com.application.springboot.model.Like;
import com.application.springboot.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;


    public int countTotalLikes(User user) {
       return likeRepository.countDistinctByLikedTo(user);
    }

    public List<Like> getLikedUsers(User likedTo) {
        return  likeRepository.getDistinctByLikedTo(likedTo);
    }

    public Like saveLiked(Like like){
        return likeRepository.save(like);
    }

    public Like updateLike( int id,boolean status) {
        return  likeRepository.updatelikeStauts(id,status);
    }
    public int deleteLikes(int id){
        return likeRepository.deleteById(id);
    }
}
