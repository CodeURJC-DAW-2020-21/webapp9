import { NONE_TYPE } from '@angular/compiler';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Events } from 'src/app/models/event.model';
import { EventService } from 'src/app/services/event.service';
import { LoginService } from 'src/app/services/login.service';
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

  constructor(private eService: EventService, private uService: UserService,  private router: Router,private lService:LoginService,private activatedRoute:ActivatedRoute) { }

  ngOnInit(): void {
    if(!this.lService.isAdmin()){
      this.router.navigate([""]);
    }else{
      this.id= this.activatedRoute.snapshot.params['id'];
      this.eService.getSingleEvent(this.id).subscribe(event => this.event=event);
    }
  }
}
