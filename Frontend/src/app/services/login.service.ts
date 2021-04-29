import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { UserService } from './user.service';

const BASE_URL = 'https://localhost:8443/api/auth/';

@Injectable({ providedIn: 'root' })
export class LoginService {

    constructor(private http: HttpClient,private userService:UserService) {
    }

    

    logIn(user: string, pass: string) {

        this.http.post(BASE_URL + "/login", 
        { 
            username: user, 
            password: pass 
        }, 
        { 
            withCredentials: true 
        }).subscribe(
                (response) => this.userService.getMe(),
                (error) => alert("Wrong credentials")
            );

    }

    logOut() {

        return this.http.post(BASE_URL + '/logout', { withCredentials: true })
            .subscribe((resp: any) => {
                console.log("LOGOUT: Successfully");
                this.userService.logOut();
            });

    }

    
}