import { Component, Injectable, OnInit } from '@angular/core';
import { Events } from 'src/app/models/event.model';
import {EventService } from 'src/app/services/event.service';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

@Injectable({ providedIn: 'root'})
export class AdminComponent implements OnInit {

  events: Events[] = [];
  constructor(private eService: EventService) { } 

  ngOnInit(){
    this.eService.getEvents(0).subscribe(events => this.events = events)
  }
  editEvent(id:number){}
  seeGraph(id:number){}
  deleteEvent(id:number){
    this.eService.deleteEvent(id);
  }


}
