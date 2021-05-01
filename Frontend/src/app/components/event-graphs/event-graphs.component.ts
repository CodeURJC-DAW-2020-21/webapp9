import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from 'src/app/services/event.service';
import { UserService } from 'src/app/services/user.service';
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
  constructor(private eService: EventService, private uService: UserService,private route: ActivatedRoute, private router: Router) {}
  

  ngOnInit(): void {
    if (this.uService.isAdmin()){
      let id= this.route.snapshot.params['id'];
      this.eService.getSingleEventData(id).subscribe(event => this.event=event);
      this.plazasLibres = this.event?.disponible;
      this.likes = this.event?.restante;
    } else {
      this.router.navigate([""]);
    }
  }
}