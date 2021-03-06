import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private uService:UserService,private router: Router) { }

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
      }).subscribe(
        (response) => {
          this.router.navigate(['login']);
          alert("Bienvenido")
      }
        ,
        (error) => alert("Email ya utilizado")
    );
  }

}
