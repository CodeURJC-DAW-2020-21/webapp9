import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  constructor(private uService: UserService) { }
  pass: boolean = false;
  me: User | undefined;
  ngOnInit(): void {
    this.me = this.uService.currentUser();
  }
  setPass(aux: boolean) {
    this.pass = aux;
  }

  postPassword(event: any, pass: string, newPass: string) {
    if(this.me!=undefined){
      this.uService.postUser({
        name:this.me.name,
        lastName:this.me.lastName,
        passwordHash:newPass,
        email:this.me.email
      })
    }
  }
  postName(event: any, name:string, lastName:string, image:string){
    if(this.me!=undefined){
      this.uService.postUser({
        name:name,
        lastName:lastName,
        passwordHash:"",
        email:this.me.email
      })
    }
    
  }

}