package com.application.springboot.repository.photostorage;

import com.application.springboot.model.Photos;
import com.application.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoStorageRepository extends JpaRepository<Photos,Integer> {

    List<Photos> findAllByPrincipalName(User user);
}
