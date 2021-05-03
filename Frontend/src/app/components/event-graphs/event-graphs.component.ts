import { Component, Injectable, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EventService } from 'src/app/services/event.service';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import { EventDATA } from '../../models/eventDATA.model';

@Component({
  selector: 'app-event-graphs',
  templateUrl: './event-graphs.component.html',
  styleUrls: ['./event-graphs.component.css']
})

@Injectable({ providedIn: 'root'})
export class EventGraphsComponent implements OnInit {

  event: EventDATA | undefined
  constructor(private eService: EventService, private route: ActivatedRoute, private router: Router,private lService:LoginService) {}
  

  ngOnInit() {
    
    if (this.lService.isAdmin()){
      let id= this.route.snapshot.params['id'];
      this.eService.getSingleEventData(id).subscribe(event => {this.event = event; console.log(this.event)});
      
    } else {
      this.router.navigate([""]);
    }
  }
}