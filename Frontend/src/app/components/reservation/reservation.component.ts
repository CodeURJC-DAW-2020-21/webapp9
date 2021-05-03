import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {

  hourSelected: number=0;
  daySelected: String='';
  typeSelected: String='';
  hours: [String,number][] = [['9:00-10:00',0],['10:00-11:00',1],['11:00-12:00',2],['12:00-13:00',3],['13:00-14:00',4],['17:00-18:00',5],['18:00-19:00',6],['19:00-20:00',7],['20:00-21:00',8]];
  constructor(private uService:UserService, private router: Router,private lService:LoginService) { }
  isLoged:boolean = false;
  full:String = "";

  ngOnInit(): void {
    this.isLoged=!this.lService.isLogged();
  }

  radioChangeHandler (event: any){
    this.hourSelected= event.target.value
  }
  dayChangeHandler (event: any){
    this.daySelected= event.target.value
  }
  typeChangeHandler (event: any){
    this.typeSelected= event.target.value
  }
}
