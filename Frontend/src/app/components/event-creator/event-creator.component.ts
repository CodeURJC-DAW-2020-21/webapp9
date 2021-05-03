import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-event-creator',
  templateUrl: './event-creator.component.html',
  styleUrls: ['./event-creator.component.css']
})
export class EventCreatorComponent implements OnInit {

  constructor(private uService: UserService, private router: Router,private lService:LoginService) { }

  ngOnInit(): void {
    if(!this.lService.isAdmin()){
      this.router.navigate(["login"]);
    }
  }

}
