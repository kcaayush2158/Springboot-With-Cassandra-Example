import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LocalStorageService } from 'ngx-webstorage';
import { LikesComponent } from '../likes/likes.component';
import { EventEmmitterService } from './event-emmitter.service';
import { Topics } from './topics';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  photoes:any =[];
  user;
  totalLikes;

  formTopic:FormGroup;


  topicName:string;
  topics:any=[];
  topic = {topicAnswer:'',topicName:'',topicQuestion:''};

  constructor(private formBuilder :FormBuilder,private http: HttpClient,private toastr :ToastrService, private spinner: NgxSpinnerService,private localStorage:LocalStorageService,private eventEmmitter :EventEmmitterService) { 
  }

  get registerFormControl() {
    return this.formTopic.controls;
  }

  get topicQuestion() {
    return this.formTopic.get('topicQuestion');
  }

  get topicAnswer() {
    return this.formTopic.get('topicAnswer');
  }
  get topicNames() {
    return this.formTopic.get('topicName');
  }


  customOptions: OwlOptions = {
    loop: true,
    mouseDrag: false,
    touchDrag: false,
    pullDrag: false,
    dots: false,
    navSpeed: 700,
    navText: ['', ''],
    responsive: {
      0: {
        items: 1
      },
      400: {
        items: 2
      },
      740: {
        items: 3
      },
      940: {
        items: 4
      }
    },
    nav: true
  }
  
    
 
  ngOnInit() {
    
    this.formTopic = this.formBuilder.group({
    topicName: "",
     topicQuestion : "",
      topicAnswer:""
    });


    this.user= this.localStorage.retrieve('user');
    this.loadTopic();

    this.loadAuthenticatedUserPhotoes();

    
    if(this.eventEmmitter.subsVar == undefined){
      this.eventEmmitter.subsVar == this.eventEmmitter.countLikesFunction.subscribe((likes:any)=>{
        console.log(likes);  
        this.countLikes(likes);
    
      })
    }
    
     
  }
countLikes(likes:any){
  this.totalLikes= likes;
  console.log(this.totalLikes);

}
  loadAuthenticatedUserPhotoes(){
 this.spinner.show();
  const user= this.localStorage.retrieve('user');

     var url = "http://localhost:8080/api/principal/user/photo?email="+user.email;
     this.http.get(url).subscribe((data)=>{
       this.spinner.hide();
       this.photoes = data;
     });
   }

   
deletePhoto(id:number){
  const user= this.localStorage.retrieve('user');
  var url = "http://localhost:8080/api/photos/"+id +"/delete?email="+user.email;
  this.http.get(url).subscribe((data)=>{
    this.spinner.hide();
    this.photoes = data;
  });
}

loadTopic(){ 

  const user = this.localStorage.retrieve('user');
  this.http.get("http://localhost:8080/api/topic/all?id="+user.id,{responseType:'json'}).subscribe((data)=>{
    this.topics =data;
  })
}


saveTopic(){
  const user = this.localStorage.retrieve('user');
  this.http.post('http://localhost:8080/api/topic/save?id='+user.id+'&topicName='+this.topic.topicName+'&topicQuestion='+this.topic.topicQuestion+'&topicAnswer='+this.topic.topicAnswer,{}).subscribe((data)=>{
    if(data != 0){
      this.toastr.success('success','Topic added');
    }else{
      this.toastr.error('error','Already existed try another one');
    }
  });


}
}

