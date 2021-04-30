import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Events } from 'src/app/models/event.model';
import { EventService } from 'src/app/services/event.service';

@Component({
  selector: 'app-single-event',
  templateUrl: './single-event.component.html',
  styleUrls: ['./single-event.component.css']
})
export class SingleEventComponent implements OnInit {
  event:Events|undefined;
  constructor(private activatedRoute:ActivatedRoute,eService: EventService) { 
    let id= activatedRoute.snapshot.params['id'];
    eService.getSingleEvent(id).subscribe(event => this.event=event);
  }

  ngOnInit(): void {
  }

}
