import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private uService:UserService,private router:Router,private lService:LoginService) { }
  isAdmin:boolean = false;
  isLoged:boolean = false;
  
  @Input()
  site:string ="";


  ngOnInit(): void {
    this.isAdmin = this.lService.isAdmin();
    this.isLoged = this.lService.isLogged();
  }

  logOut(){
    this.lService.logOut();
    this.router.navigate(['login'],{skipLocationChange:true});
  }

  goProfile(){
    if(this.lService.isLogged()){
      this.router.navigate(['profile']);
    } else{
      this.router.navigate(['login']);
    }
  }

}
