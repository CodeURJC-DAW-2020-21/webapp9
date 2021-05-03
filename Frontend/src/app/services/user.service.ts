import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { Events } from "../models/event.model";
import { User } from "../models/user.model";
import { UserDTO } from "../models/userDTO.model";
const BASE_URL = '/api/user/';
@Injectable({ providedIn: 'root' })
export class UserService {


    

    constructor(private http: HttpClient){
    }

    setImage(file:File){
        let formdata: FormData = new FormData();
        formdata.append('file', file);
        return this.http.post(BASE_URL+"image",formdata)
        .pipe(catchError(error => this.handleError(error)));
    }

    getImage(){
        return this.http.get(BASE_URL+"image")
        .pipe(catchError(error => this.handleError(error)));
    }

    getRecomendatedEvents():Observable<Events[]>{
        return this.http.get<Events[]>(BASE_URL+"recomendatedEvents")
        .pipe(catchError(error => this.handleError(error)));
    }

    getMyReservates():Observable<String[]>{
        return this.http.get<String[]>(BASE_URL+"myReservates")
        .pipe(catchError(error => this.handleError(error)));
    }

    getLikedEvents():Observable<Events[]>{
        return this.http.get<Events[]>(BASE_URL+"likedEvents")
        .pipe(catchError(error => this.handleError(error)));
    }

    postUser(u:UserDTO){
        console.log(u);
        return this.http.post(BASE_URL,u)
        .pipe(catchError(error => this.handleError(error)));
    }

    putUser(u:UserDTO){
        return this.http.put(BASE_URL,u)
        .pipe(catchError(error => this.handleError(error)));
    }

    getSrc(){
        return this.http.get(BASE_URL+"src")
        .pipe(catchError(error => this.handleError(error)));
    }


    

    private handleError(error: HttpErrorResponse) {
        console.error(error);
        return throwError(
          'Server error (' + error.status + '): ' + error.message
        );
      }

      like(id:number){
          this.http.post(BASE_URL+"like",id).subscribe(
            (response) => alert("Has dado like correctamente"),
            (error) => alert("No has podido dar like")
        );
      }
    
}