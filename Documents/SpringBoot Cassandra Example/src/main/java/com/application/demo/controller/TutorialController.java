package com.application.demo.controller;

import com.application.demo.model.Tutorial;
import com.application.demo.service.TutorialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    public TutorialsService tutorialsService;

    @PostMapping("/save/tutorails")
    public Tutorial save(@RequestBody Tutorial responseTutorial){
        Tutorial tutorial = new Tutorial();
        tutorial.setId(UUID.randomUUID());
        tutorial.setDescription(responseTutorial.getDescription());
        tutorial.setPublished(true);
        tutorial.setTitle(responseTutorial.getTitle());
       return tutorialsService.saveTutorials(tutorial);

    }

    @GetMapping("/tutorials/all")
    public List<Tutorial> findAllTutorials(Tutorial tutorial){
       return tutorialsService.findAll(tutorial);
    }

    @PostMapping("/tutorials/{id}/update")
    public Tutorial getById(@RequestBody Tutorial tutorial,@PathVariable("id") UUID id){
        Optional<Tutorial> existingTutorial = tutorialsService.findTutorialById(id);
        System.out.println("processing");
        if(existingTutorial.isPresent()){
           Tutorial _tutorial = existingTutorial.get();
           _tutorial.setTitle(tutorial.getTitle());
           _tutorial.setPublished(tutorial.isPublished());
           _tutorial.setDescription(tutorial.getDescription());
          return tutorialsService.saveTutorials(_tutorial);
        }
        return tutorialsService.saveTutorials(tutorial);
    }

    @DeleteMapping("/tutorial/{id}/delete")
    public void deleteTutorial(@PathVariable("id") UUID id){
        tutorialsService.deleteTutorial(id);

    }

//    @DeleteMapping("/tutorail/all/delete")
//    public void deleteAllTutorial(){
//        tutorialsService.deleteTutorial();
//
//    }

//    @GetMapping("/tutorials/published")
//    public List<Tutorial> findByPublished() {
//        return tutorialsService.findTutorialByPublished(true);
//    }

}
