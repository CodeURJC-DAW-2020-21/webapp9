import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';

const BASE_URL = '/api/reservate';

@Injectable({ providedIn: 'root' })
export class LoginService {

    constructor(private http: HttpClient) {
    }

    reservate(type: string, day: string, hour: BigInteger, email: string) {
         return this.http.post(BASE_URL, 
         { 
             type: type, 
             day: day, 
             hour: hour, 
             email: email 
        },
        { 
            withCredentials: true 
        });
    }
}