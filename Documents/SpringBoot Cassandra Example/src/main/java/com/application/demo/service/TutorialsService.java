package com.application.demo.service;

import com.application.demo.model.Tutorial;
import com.application.demo.repository.MyCasssandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TutorialsService {

    @Autowired
    public MyCasssandraRepository cassandraRepository;

    public Tutorial saveTutorials(Tutorial tutorial){
        return cassandraRepository.save(tutorial);
    }

    public void deleteTutorial(UUID id){
        cassandraRepository.deleteById(id);
    }

    public List<Tutorial> findAll(Tutorial tutorial){
        return  cassandraRepository.findAll();
    }


    public Optional<Tutorial> findTutorialById(UUID id) {
        return cassandraRepository.findById(id);
    }

    public Optional<Tutorial> find(UUID id) {
        return cassandraRepository.findById(id);
    }

    public Tutorial updateTutorials(Tutorial tutorial){
        return  cassandraRepository.save(tutorial);
    }

//    public List<Tutorial> findTutorialByPublished(boolean b) {
//        return cassandraRepository.f
//    }
}
