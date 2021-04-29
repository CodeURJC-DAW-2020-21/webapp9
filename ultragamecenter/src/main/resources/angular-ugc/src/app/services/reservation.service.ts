import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';

const BASE_URL = '/api/auth';

@Injectable({ providedIn: 'root' })
export class LoginService {

    constructor(private http: HttpClient) {
        this.reservate();
    }

    reservate(type: string, day: string, hour: BigInteger, email: string) {
         return this.http.post('/api/reservate/', { type: type, day: day, hour: hour, email: email },{ withCredentials: true });
    }
}