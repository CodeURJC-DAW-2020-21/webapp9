import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { User } from "../models/user.model";
import { UserDTO } from "../models/userDTO.model";
const BASE_URL = 'https://localhost:8443/api/user/';
@Injectable({ providedIn: 'root' })
export class UserService {


    logged: boolean = false;
    user: User | undefined;

    constructor(private http: HttpClient){
    }

    getImage(){
        return this.http.get(BASE_URL+"image")
        .pipe(catchError(error => this.handleError(error)));
    }

    getRecomendatedEvents():Observable<Event[]>{
        return this.http.get<Event[]>(BASE_URL+"recomendatedEvents")
        .pipe(catchError(error => this.handleError(error)));
    }

    getMyReservates():Observable<String[]>{
        return this.http.get<String[]>(BASE_URL+"myReservates")
        .pipe(catchError(error => this.handleError(error)));
    }

    getLikedEvents():Observable<Event[]>{
        return this.http.get<Event[]>(BASE_URL+"likedEvents")
        .pipe(catchError(error => this.handleError(error)));
    }

    postUser(u:UserDTO){
        return this.http.post(BASE_URL,u)
        .pipe(catchError(error => this.handleError(error)));
    }

    putUser(u:UserDTO){
        return this.http.put(BASE_URL,u)
        .pipe(catchError(error => this.handleError(error)));
    }


    getMe() {
        this.http.get(BASE_URL+"/me", 
          { 
            withCredentials: true 
          }).subscribe(
            response => {
                this.user = response as User;
                this.logged = true;
            },
            error => {
                if (error.status != 404) {
                    console.error('Error when asking if logged: ' + JSON.stringify(error));
                }
            }
        );

    }

    logOut(){
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

    private handleError(error: HttpErrorResponse) {
        console.error(error);
        return Observable.throw(
          'Server error (' + error.status + '): ' + error.message
        );
      }
    
}