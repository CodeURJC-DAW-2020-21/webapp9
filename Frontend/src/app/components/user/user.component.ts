import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Events } from 'src/app/models/event.model';
import { User } from 'src/app/models/user.model';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor(private lService: LoginService, private router:Router, private uService:UserService) { }
  reservates: String[] = [];
  likedEvents: Events[] = [];
  recomendatedEvents: Events[] = []
  Me: User | undefined;

  ngOnInit(): void {
    console.log("usuario conectado:",this.lService.currentUser())
    if (!this.lService.isLogged()){
      this.router.navigate(['login']);
    }
    this.Me = this.lService.currentUser();
    this.uService.getMyReservates().subscribe(reservates => {
      reservates.forEach(reserve => {
        this.reservates.push(reserve);
      })
    });
    this.uService.getLikedEvents().subscribe(likedEvents => {
      likedEvents.forEach(event => {
        this.likedEvents.push(event);
      })
    });
    this.uService.getRecomendatedEvents().subscribe(recomendatedEvents => {
      recomendatedEvents.forEach(event => {
        this.recomendatedEvents.push(event);
      })
    });
    console.log(this.Me);
    console.log(this.recomendatedEvents);
    console.log(this.likedEvents);
  }

}
