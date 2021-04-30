import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EventService } from 'src/app/services/event.service';
import { EventDATA } from '../../models/eventDATA.model';

@Component({
  selector: 'app-event-graphs',
  templateUrl: './event-graphs.component.html',
  styleUrls: ['./event-graphs.component.css']
})

@Injectable({ providedIn: 'root'})
export class EventGraphsComponent implements OnInit {

  likes:number | undefined;
  plazasLibres:number | undefined;
  
  event: EventDATA|undefined
  constructor(private eService: EventService, private route: ActivatedRoute) {}
  

  ngOnInit(): void {
    let id= this.route.snapshot.params['id'];
    this.eService.getSingleEventData(id).subscribe(event => this.event=event);
    this.plazasLibres = this.event?.disponible;
    this.likes = this.event?.restante
  }
}