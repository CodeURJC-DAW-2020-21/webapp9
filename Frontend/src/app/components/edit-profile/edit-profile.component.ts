import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import {User} from 'src/app/models/user.model';
@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  user: User | void | undefined;
  constructor(private uService: UserService, private router: Router) { }

  ngOnInit(): void {
    if(!this.uService.isLogged()){
      this.router.navigate([""]);
    }else{
      this.user = this.uService.getMe();
    }
  }

}
