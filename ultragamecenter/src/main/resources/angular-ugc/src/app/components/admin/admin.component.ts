import { Component, Injectable, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})

@Injectable({ providedIn: 'root'})
export class AdminComponent implements OnInit {

  events: Event[] = []
  constructor(private service: AdminService) { } 

  ngOnInit(){
    this.service.getEvents().subscribe(events => this.events = events)
  }
}
