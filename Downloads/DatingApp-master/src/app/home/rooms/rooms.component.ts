import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {Component, Input, OnInit} from '@angular/core';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BehaviorSubject, Observable, timer } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap, concatMap, map, filter, take } from 'rxjs/operators';
// search module
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { Chatroom } from 'src/app/user';
import { LocalStorageService } from 'ngx-webstorage';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { ToastrService } from 'ngx-toastr';
import { ToastrModule } from 'ngx-toastr';
import { interval } from 'rxjs/internal/observable/interval';
import { startWith } from 'rxjs/operators';
import { FormBuilder, FormControl, FormGroup, FormsModule, Validators } from '@angular/forms';


@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.css']
})



export class RoomsComponent implements OnInit {
  chatRoomId:number;
  currentItem ;
  loadShoutOut: any = [];
  myRoomsCount: string;
  message: string;
  allRoomsCount: any;
  createRoomForm: FormGroup;

  room = new Chatroom();
  $loading = new BehaviorSubject('');
  createChatrom: any = [];
  user: any = [];

  notEmptyPost = true;
  notscrolly = true;

  constructor(private http: HttpClient, private formBuilder: FormBuilder, private forms: FormsModule, private toaster: ToastrService, private spinner: NgxSpinnerService, private toastr: ToastrService, private route: Router, private activatedRouter: ActivatedRoute, private localStorage: LocalStorageService) {

  }

  ngOnInit() {
    this.route.navigate(['/rooms/all-rooms']);
    this.user = localStorage.getItem('user');
    this.countMyRoom();
    this.loadShoutOuts();

    this.createRoomForm = this.formBuilder.group({
      chatRoomName: ['', [Validators.required, Validators.minLength(5), Validators.required, Validators.maxLength(20)]],
      roomDescription: ['', [Validators.required, Validators.minLength(20), Validators.required, Validators.maxLength(100)]],
      roomType: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(5), Validators.required, Validators.maxLength(20)]],
    });


  }


  get registerFormControl() {
    return this.createRoomForm.controls;
  }


  get chatRoomName() {
    return this.createRoomForm.get('chatRoomName');
  }

  get roomDescription() {
    return this.createRoomForm.get('roomDescription');
  }

  get roomType() {
    return this.createRoomForm.get('roomType');
  }

  get password() {
    return this.createRoomForm.get('password');
  }


  onScroll() {
    if (this.notscrolly && this.notEmptyPost) {
      this.spinner.show();
      this.notscrolly = false;
      this.loadShoutOuts();
      console.log('scrolled');
    }
  }


  loadShoutOuts() {


    const url = 'http://localhost:8080/api/chatrooms/shoutout/all';
    interval(3000)
      .pipe(
        startWith(0),
        switchMap(() =>
          this.http.get(url, { responseType: 'json' })
        )
      )
      .subscribe(res => {
        this.loadShoutOut = res;
        this.loadNextPost();
        console.log('scrolled');
       })  ;
  }
  loadNextPost() {
    const url = 'http://localhost:8080/api/chatrooms/shoutout/all';
    // return last post from the array
    const lastPost = this.loadShoutOut[this.loadShoutOut.length - 1];
    // get id of last post
    const lastPostId = lastPost.id;
    // sent this id as key value pare using formdata()
    const dataToSend = new FormData();
    dataToSend.append('id', lastPostId);
    // call http request

    this.http.post(url, dataToSend)
      .subscribe( (data: any) => {
        console.log("Data"+ data);
        const newPost = data[0];
        this.spinner.hide();
        if (newPost.length === 0 ) {
          this.notEmptyPost =  false;
        }

        // add newly fetched posts to the existing post
        this.loadShoutOut = this.loadShoutOut.concat(newPost);
        this.notscrolly = true;
      });
  }
  createShoutOuts() {

    const user = this.localStorage.retrieve('user');
    const url = 'http://localhost:8080/api/chatrooms/shoutout/save?email='+user.email+'&message='+this.message;
    const request = JSON.stringify({
      message: this.message,
      email: user.email
    })


    let params = new HttpParams();
        params = params.append('message', this.message);
        params = params.append('email', user.email);

        let headers = new HttpHeaders();
        headers = headers.set('Content-Type', 'application/json');

    this.http.post(url,{params}).subscribe((data) => {
      this.toaster.success('success', 'Shout Out done');
      this.loadShoutOut();
    });
  }

  createChatroom() {
    // const params = new HttpParams()
    //   .set('chatRoomName', this.chatroom.chatRoomName)
    //   .set('password', this.chatroom.password)
    //   .set('chatRoomDescription', this.chatroom.chatRoomDescription)
    //   .set('roomType', this.chatroom.roomType);


    const users = this.localStorage.retrieve('user');
    const url = 'http://localhost:8080/api/chatroom/create?chatRoomName='+this.chatRoomName.value +'&roomDescription='+this.roomDescription.value+'&roomType='+this.roomType.value+'&password='+this.password.value+'&email='+users.email;

    const headers = { 'content-type': 'application/json' }

    this.http.post(url,{headers}).subscribe((data) => {
      console.log(data);
      this.spinner.show();
      this.countMyRoom();
    }
    )
  }

  countMyRoom() {
    const user = this.localStorage.retrieve('user');
    const url = this.http.get('http://localhost:8080/api/chatroom/my/count?email=' + user.email, { responseType: 'text' });
    console.log(url);
    this.$loading.pipe(
      switchMap(_ => timer(0, 100000).pipe(
        concatMap(_ => url),
        map((response: any) => {
          this.myRoomsCount = response;
          console.log(response);
          return response;
        })
      ).pipe(filter(data => data.generated == true))
        .pipe(take(1))
      )).subscribe((data) => {

        this.myRoomsCount = data;
      });

  }



}

