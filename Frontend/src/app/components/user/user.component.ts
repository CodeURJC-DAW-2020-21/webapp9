import { Component, OnInit } from '@angular/core';
import { Events } from 'src/app/models/event.model';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor(private uService: UserService) { }
  reservates: String[] = [];
  likedEvents: Events[] = [];
  recomendatedEvents: Events[] = []
  Me: User | undefined;

  ngOnInit(): void {
    this.Me = this.uService.currentUser();
    this.uService.getMyReservates().subscribe(reservates => {
      for (let reserva in reservates) {
        this.reservates.push(reserva);
      }
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
