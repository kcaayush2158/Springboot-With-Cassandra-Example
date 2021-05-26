import {Component, Input, OnInit} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgxSpinnerModule, NgxSpinnerService } from 'ngx-spinner';
import { BehaviorSubject, interval, timer } from 'rxjs';
import { switchMap, concatMap, map, filter, take } from 'rxjs/operators';
import { startWith } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { FormControl, FormGroup } from '@angular/forms';
import { Chatroom } from 'src/app/user';

@Component({
  selector: 'app-allroom',
  templateUrl: './allroom.component.html',
  styleUrls: ['./allroom.component.css']
})
export class AllroomComponent implements OnInit {
  @Input() search ='';
    allRooms: any=[];
    allRoomsCount;


  constructor(private http:HttpClient,private spinner:NgxSpinnerService ,private toastr :ToastrService) {



  }

  ngOnInit(): void {
    this.loadChatRooms();
    console.log('search' + this.search)
  }

  loadChatRooms() {
    this.spinner.show();
    const url = 'http://localhost:8080/api/chatrooms/all';

    interval(20000)
    .pipe(
      startWith(0),
      switchMap(() =>    this.http.get(url,{responseType:'json'}))
     ).subscribe((data) =>{

      this.allRoomsCount = data;
       this.allRooms =data;
       this.spinner.hide();
       this.toastr.success('success','Room refreshed');
    },(err)=>{
        console.log('Error')
        this.toastr.error('error','Unable to load room');
    });




  }

}

