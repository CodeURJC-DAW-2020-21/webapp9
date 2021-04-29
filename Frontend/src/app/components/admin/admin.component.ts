import { Component, Injectable, OnInit } from '@angular/core';
import { EventService } from 'src/app/services/event.service';
import { EventDTO } from '../../models/eventDTO.model';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

@Injectable({ providedIn: 'root'})
export class AdminComponent implements OnInit {

  events: EventDTO[] = []
  constructor(private service: EventService) { } 

  ngOnInit(){
    this.service.getEvents(0).subscribe(events => this.events = events);
  }
}
