import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Events } from 'src/app/models/event.model';
import { EventSRC } from 'src/app/models/eventSRC.model';
import { EventService } from 'src/app/services/event.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-single-event',
  templateUrl: './single-event.component.html',
  styleUrls: ['./single-event.component.css']
})
export class SingleEventComponent implements OnInit {
  event:Events|undefined;
  principal:string="/images/";
  galery:string[]=[]
  loged:boolean=false;

  constructor(private activatedRoute:ActivatedRoute,private eService: EventService,private lService:LoginService) { 
    let id= activatedRoute.snapshot.params['id'];
    eService.getEvensSRC(id).subscribe(url => 
      {
        this.principal=url.principal;
        for(let aux of url.galery){
          this.galery.push(aux);
        }
        console.log(url.galery);
      }
      );
    console.log(this.galery);
    eService.getSingleEvent(id).subscribe(event => this.event=event);
  }

  ngOnInit(): void {
    this.loged=this.lService.isLogged();
  }

  like(id:number){
    if(this.lService.isLogged()){
      this.eService.likeEvent(id).subscribe(
        (response)=>alert("Has dado like correctamente"),
        (error)=> alert("no has podido dar like")
      )
    }else{
      alert("No estás logeado");
    }
  }

}
