import { Component, Injectable, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EventService } from 'src/app/services/event.service';
import { UserService } from 'src/app/services/user.service';
import { Events } from '../../models/event.model';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

@Injectable({ providedIn: 'root'})
export class AdminComponent implements OnInit {

  events: Events[] = [];
  constructor(private eService: EventService, private uService: UserService, private router: Router) { } 

  ngOnInit(){
    this.eService.getEvents(0).subscribe(events => this.events = events)
  }
  deleteEvent(id:number){
    if (this.uService.isAdmin()){
      this.eService.deleteEvent(id).subscribe(() => console.log("user deleted"));
    }else{
      this.router.navigate([""]);
    }
    
  }

  redirectGraphs(id: number){
    this.router.navigate(['graph-event/'+id]);
  }

  redirectEditEvent(id: number){
    this.router.navigate(["edit-event/"+id]);
  }

}
