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

  constructor(private uService:UserService) { }
  reservates:String[]=[];
  likedEvents:Events[]=[];
  recomendatedEvents:Events[]=[]
  name:String="";
  lastName:String="";
  email:String="";
  image:File|undefined;
  Me:User|undefined;

  ngOnInit(): void {
    this.uService.getImage().subscribe(image => this.image=image as File)
    this.Me = this.uService.currentUser();
    this.uService.getMyReservates().subscribe(reservates => this.reservates=reservates);
    this.uService.getLikedEvents().subscribe(likedEvents => this.likedEvents=likedEvents);
    this.uService.getRecomendatedEvents().subscribe(recomendatedEvents => this.recomendatedEvents=recomendatedEvents);
  }

}
