package com.application.springboot;

import com.application.springboot.model.Topic;
import com.application.springboot.model.User;
import com.application.springboot.service.TopicService;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public ResponseEntity<List<Topic>> findALlTopic( @RequestParam("id")int id){
        User  user = this.userService.findUserById(id);
        return ResponseEntity.ok().body(this.topicService.findTopic(user));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteTopic( @RequestParam("id") int id){
        this.topicService.deleteTopic(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateTopic(@RequestParam("id") int id, @RequestParam(value = "topicName", required = false)String topicName, @RequestParam("topicQuestion")String topicQuestion, @RequestParam("topicAnswer")String topicAnswer){
        this.topicService.updateTopic(topicAnswer, topicQuestion, topicName,id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Topic> saveTopic( @RequestParam(value = "id",required = false)int id,@RequestParam("topicName")String topicName, @RequestParam("topicQuestion")String topicQuestion, @RequestParam("topicAnswer")String topicAnswer){
        User user = this.userService.findUserById(id);
        Topic topic;
        if(user !=null){
            List<Topic> topics = this.topicService.findTopic(user);

            for(Topic existedTopic: topics){
                if (!existedTopic.getTopicName().equals(topicName) || !existedTopic.getTopicQuestion().equals(topicQuestion)){

                    return getTopicResponseEntity(topicName, topicQuestion, topicAnswer, user, existedTopic);
                }else{
                    System.out.println("topic already existed");
                    return ResponseEntity.badRequest().build();
                }
            }


        }

        else{
            topic = new Topic();
            topic.setTopicName(topicName);
            topic.setTopicAnswer(topicAnswer);
            topic.setTopicQuestion(topicQuestion);
            topic.setUser(user);
            topicService.saveTopic(topic);
        }


        return ResponseEntity.ok().build();
    }

    private ResponseEntity<Topic> getTopicResponseEntity(@RequestParam("topicName") String topicName, @RequestParam("topicQuestion") String topicQuestion, @RequestParam("topicAnswer") String topicAnswer, User user, Topic existedTopic) {
        existedTopic.setTopicName(topicName);
        existedTopic.setTopicAnswer(topicAnswer);
        existedTopic.setTopicQuestion(topicQuestion);
        existedTopic.setUser(user);
        this.topicService.saveTopic(existedTopic);
        System.out.println("saving topic");
        return ResponseEntity.ok().body(existedTopic);
    }


}
