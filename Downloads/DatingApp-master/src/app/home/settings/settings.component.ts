import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { LocalStorageService } from 'ngx-webstorage';
import { PasswordChange } from './settings';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  users:any=[];
  formChangePassword :  FormGroup;
  passwordChange= new PasswordChange();

  constructor(private localStorage :LocalStorageService,private formBuilder :FormBuilder,private tostr:ToastrService,private spinner :NgxSpinnerService,private router:Router,private http:HttpClient ) {
    
   }

  ngOnInit(): void {
    this.users = this.localStorage.retrieve('user');
  

   this.formChangePassword = this.formBuilder.group({
     oldPassword : ['', [Validators.required, Validators.minLength(5), Validators.required, Validators.maxLength(20)]],
     newPassword:  ['', [Validators.required, Validators.minLength(5), Validators.required, Validators.maxLength(20)]]
   });

  }

  
  get registerFormControl() {
    return this.formChangePassword.controls;
  }
  
  get oldPassword() {
    return this.formChangePassword.get('oldPassword');
  }

  get newPassword() {
    return this.formChangePassword.get('newPassword');
  }


  changePassword(){
    var headers = new HttpHeaders();
    headers = headers.set("Content-Type", "application/json");

    const user= this.localStorage.retrieve('user');
    const url="http://localhost:8080/api/settings/password/save?oldPassword="+this.oldPassword.value+"&password="+this.newPassword.value+"&email="+user.email;
    console.log(url);
    this.spinner.show(); 
 
    this.http.post(url, {'headers':headers}).subscribe((data)=>{
      if(data == 1){
        this.spinner.hide();
        this.tostr.success('Success','Password has been changed successfully');
      }else{
        this.tostr.error('Error','BAD CREDENTIALS');
      }
      console.log(data);

     },(error)=> this.tostr.error('error','Failed to changed password'));

  }

  deleteProfile(){
    const user= this.localStorage.retrieve('user');
    const url="http://localhost:8080/api/profile/delete?email="+user.email;
    this.spinner.show(); 
    this.http.post(url,{}).subscribe((data)=>{
      if(data !=null){
        this.spinner.hide();
        this.router.navigate['/logout'];
        this.tostr.success('Success','Profile has been changed successfully');
      }else{
        this.tostr.error('Error','Unable to delete the profile');
      }
        
     },(error)=> this.tostr.error('error','Failed to delete  Profile'));

  }

  uploadPhoto(){

    
  }

}
