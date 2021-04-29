import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Event } from '../models/event.model';

@Injectable({ providedIn: 'root' })
export class BooksService {
  constructor(private http: HttpClient) { }
  getEvents(page: BigInteger) {
    return this.http.get("/api/event/?page="+page);
  }
  getSingleEvent(id:BigInteger){
    return this.http.get("/api/event/"+id) as Event;
  }

  editEvent(event:Event,id:BigInteger){
    return this.http.put("/api/event/"+id,event);
  }
}