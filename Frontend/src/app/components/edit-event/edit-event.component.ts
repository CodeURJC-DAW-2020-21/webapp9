import { NONE_TYPE } from '@angular/compiler';
import { Component, Input, OnInit } from '@angular/core';
import { Events } from 'src/app/models/event.model';
import { EventService } from 'src/app/services/event.service';

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

  constructor(private eService: EventService) { }

  ngOnInit(): void {
    this.eService.getSingleEvent(this.id).subscribe(event => this.event=event);
  }

}
