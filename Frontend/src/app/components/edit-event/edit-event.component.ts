import { NONE_TYPE } from '@angular/compiler';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Events } from 'src/app/models/event.model';
import { EventService } from 'src/app/services/event.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-event',
  templateUrl: './edit-event.component.html',
  styleUrls: ['./edit-event.component.css']
})
export class EditEventComponent implements OnInit {
  name: String = "";
  description: String = "";
  labels: String = "";
  capacity: number = 0;
  end: String = "";
  event :Events | undefined;


  @Input()
  id: number = 0;

  constructor(private eService: EventService, private uService: UserService,  private router: Router) { }

  ngOnInit(): void {
    if(!this.uService.isAdmin()){
      this.router.navigate([""]);
    }else{
      this.eService.getSingleEvent(this.id).subscribe(event => this.event=event);
    }
  }
}
