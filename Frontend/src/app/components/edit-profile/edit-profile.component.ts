import { ThisReceiver } from '@angular/compiler';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

import { Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { LoginService } from 'src/app/services/login.service';
@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

constructor(private lService: LoginService, private router: Router, private uService:UserService) { }
  me: User | undefined;
  file : File|undefined;
  
  @ViewChild("foto", {
    read: ElementRef
  }) foto: ElementRef | undefined;

  ngOnInit(): void {
    if(!this.lService.isLogged()){
        this.router.navigate(["login"]);
    }else{
        this.me = this.lService.currentUser();
    }
  }

  postAll(event: any,name:string, lastName:string, pass: string) {
    event.preventDefault();
    let archivos = this.foto?.nativeElement.files;
    this.file = archivos.item(0);
    if(this.me!=undefined){
      this.uService.putUser({
        name:name,
        lastName:lastName,
        passwordHash:pass,
        email:this.me.email
      }).subscribe(
       (response) => this.router.navigate(['profile']),
       (error) => alert("fallo")
      );
      console.log(this.file);
      if(this.file!=undefined){
        this.uService.setImage(this.file).subscribe(
          (response) => alert("nueva imagen"),
          (error) => alert("fallo al poner tu imagen")
        )
      }
    }
    this.lService.reqIsLogged();
  }
  

}