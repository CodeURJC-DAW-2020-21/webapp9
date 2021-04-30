import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private uService:UserService) { }

  ngOnInit(): void {
  }
  Register(event: any, name: string, lastName: string,email:string,password:string) {
    
    event.preventDefault();

    this.uService.postUser(
      {
        name:name,
        passwordHash:password,
        lastName:lastName,
        email:email
      });
  }

}
