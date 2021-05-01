import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-event-creator',
  templateUrl: './event-creator.component.html',
  styleUrls: ['./event-creator.component.css']
})
export class EventCreatorComponent implements OnInit {

  constructor(private uService: UserService, private router: Router) { }

  ngOnInit(): void {
    if(!this.uService.isAdmin()){
      this.router.navigate([""]);
    }
  }

}
