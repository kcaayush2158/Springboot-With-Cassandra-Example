import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EventEmitter } from 'events';

import { OwlOptions } from 'ngx-owl-carousel-o';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LocalStorageService } from 'ngx-webstorage';
import { LikesComponent } from '../likes/likes.component';
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
  topicQuestion='';
  topicAnswer='';

  @Input() formTopic:FormGroup;
  @Output() formChange: EventEmitter = new EventEmitter()

  topicName='';
  topics:any=[];

  constructor(private formBuilder :FormBuilder,private http: HttpClient,private toastr :ToastrService, private spinner: NgxSpinnerService,private localStorage:LocalStorageService) { 
  }

  

  get registerFormControl() {
    return this.formTopic.controls;
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
    // this.formChange.emit(this.formTopic)


    // this.formTopic = this.formBuilder.group({
    //   topicName: ['', [Validators.required] ],
    //   topicQuestion : ['', [Validators.required,]],
    //   topicAnswer: ['', [Validators.required, Validators.minLength(100), Validators.maxLength(250)]],
    // });


    this.user= this.localStorage.retrieve('user');
    this.loadTopic();

    this.loadAuthenticatedUserPhotoes();

    
    // if(this.eventEmmitter.subsVar == undefined){
    //   this.eventEmmitter.subsVar == this.eventEmmitter.countLikesFunction.subscribe((likes:any)=>{
    //     console.log(likes);  
    //     this.countLikes(likes);
    
    //   })
    // }
    
     
  }

  // onChanges(): void {
  //   console.log('MyForm > onChanges', this.formTopic.value)
  //   this.formTopic.valueChanges.subscribe(value=>{
  //     this.formChange.emit(this.formTopic)
  //   })
  // }

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
  this.http.post('http://localhost:8080/api/topic/save?id='+user.id+'&topicName='+this.topicName+'&topicQuestion='+this.topicQuestion+'&topicAnswer='+this.topicAnswer,{}).subscribe((data)=>{
  console.log(data); 
  this.toastr.success('success','Topic added');
  this.loadTopic();

  }),(error)=>{  this.toastr.error('error','Topic Already existed');};


}

deleteTopic(id:number){
  this.http.post("http://localhost:8080/api/topic/delete?id="+id,{}).subscribe((data)=>{
    this.toastr.success('success','Topic deleted successfully');
    this.loadTopic();
  });

}
}

