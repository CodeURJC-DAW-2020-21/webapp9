import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { UserService } from './user.service';
import { LoginModel } from '../models/login.model';

const BASE_URL = '/api/auth';

@Injectable({ providedIn: 'root' })
export class LoginService {

    constructor(private http: HttpClient,private userService:UserService) {
    }

    logIn(login:LoginModel) {
        this.http.post(BASE_URL + "/login", login)
            .subscribe(
                (response) => this.userService.reqIsLogged(),
                (error) => alert("Contraseña o usuario no correctos")
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