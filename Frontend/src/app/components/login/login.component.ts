import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private lService: LoginService,private router: Router,private uService:UserService ) {}
  ngOnInit(): void {

  }

  logIn(event: any, user: string, pass: string) {
    event.preventDefault();
    this.lService.logIn({
      username:user,
      password:pass
    });
    if(this.uService.isLogged()){
      this.router.navigate(['profile']);
    }
    
    
  }

}
