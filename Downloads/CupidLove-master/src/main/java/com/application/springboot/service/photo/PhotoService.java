package com.application.springboot.service.photo;

import com.application.springboot.model.Photos;
import com.application.springboot.model.User;
import com.application.springboot.repository.photostorage.PhotoStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoStorageRepository photoStorageRepository;

    public Photos savePhotos(Photos photos){
    return  photoStorageRepository.save(photos);
    }

    public int deletePhotos(int id){
         photoStorageRepository.deleteById(id);
        return id;
    }

    public List<Photos> showAllPhotos(User user){
        return photoStorageRepository.findAllByPrincipalName(user);
    }



}
