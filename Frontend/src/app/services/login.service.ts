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

    logged: boolean = false;
    user: User | undefined;

    logOutt(){
        this.logged=false;
        this.user=undefined;
    }


    isLogged():boolean {
        return this.logged;
    }

    

    isAdmin():boolean {
        if (this.user && this.user.roles.indexOf('ADMIN') !== -1){
            return true;
        }
        return false;

    }

    currentUser() {
        return this.user;
    }

    logIn(login:LoginModel) {
        return this.http.post(BASE_URL + "/login", login);
    }

    reqIsLogged() {
        this.http.get("/api/user/me", { withCredentials: true }).subscribe(
            response => {
                console.log("Se esta logeando")
                this.user = response as User;
                console.log(this.user);
                this.logged = true;
            },
            error => {
                if (error.status != 404) {
                    console.error('Error when asking if logged: ' + JSON.stringify(error));
                }
            }
        );

    }

    logOut() {

        return this.http.post(BASE_URL + '/logout', { withCredentials: true })
            .subscribe((resp: any) => {
                console.log("LOGOUT: Successfully");
                this.logOutt();
            });

    }

    
}