import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private uService:UserService) { }
  isLoged:boolean = false;
  ngOnInit(): void {
    this.isLoged=this.uService.isLogged();
  }

}
