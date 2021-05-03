import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { Table } from '../models/table.model';

const BASE_URL = '/api/reservate';

@Injectable({ providedIn: 'root' })
export class ReservationService {

    constructor(private http: HttpClient) {
    }

    reservate(table:Table) {
        this.http.post(BASE_URL +"/", 
         table).subscribe(
            (response) => alert("Tu reserva se ha realizado correctamente"),
            (error) => alert("No ha reservas disponibles en la hora indicada")
        );
    }
}