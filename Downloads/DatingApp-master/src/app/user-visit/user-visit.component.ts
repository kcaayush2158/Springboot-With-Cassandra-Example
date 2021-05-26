import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-visit',
  templateUrl: './user-visit.component.html',
  styleUrls: ['./user-visit.component.css']
})
export class UserVisitComponent implements OnInit {

  user:any;
  constructor(private activatedRoute :ActivatedRoute, private http:HttpClient) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((datas)=>{
        
         this.user= this.activatedRoute.snapshot.paramMap.get('string');
     
      this.http.get("http://localhost:8080/api/user/"+this.user,{responseType:'json'}).subscribe((data)=>{
        console.log(data);
          this.user =data;
          
      });


    
    });


  }

}
