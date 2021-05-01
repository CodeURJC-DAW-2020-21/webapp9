import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private uService:UserService,private router:Router) { }
  isAdmin:boolean = false;
  isLoged:boolean = false;
  @Input()
  site:string ="";


  ngOnInit(): void {
    this.isAdmin = this.uService.isAdmin();
    this.isLoged = this.uService.isLogged();
  }

  logOut(){
    this.uService.logOut();
    this.router.navigate(['login']);
  }

}
