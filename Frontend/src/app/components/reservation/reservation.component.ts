import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  constructor(private uService:UserService) { }
  isLoged:boolean = false;
  full:String = "";
  ngOnInit(): void {
    this.isLoged=!this.uService.isLogged();
  }

}
