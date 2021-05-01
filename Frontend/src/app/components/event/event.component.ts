import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Events } from 'src/app/models/event.model';
import { EventService } from 'src/app/services/event.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  constructor(private eService: EventService, private uService: UserService) { }
  page: number = 0;
  isLoged: boolean = false;
  events: Events[] = [];
  more: boolean = true;

  ngOnInit(): void {
    this.eService.getEvents(this.page).subscribe(events => this.events = events)
    this.isLoged = this.uService.isLogged();
    this.eService.getEvents(this.page + 1).subscribe(events => this.more = events.length != 0)
  }

  nextPage() {
    this.page++;
    this.eService.getEvents(this.page).subscribe(events => {
      for (let e of events) {
        this.events.push(e);
      }
    })
    this.eService.getEvents(this.page + 1).subscribe(events => this.more = events.length != 0)
  }

  like(id:number){
    if(this.uService.isLogged()){
      this.uService.like(id);
    }else{
      alert("No estás logeado");
    }
  }

}
