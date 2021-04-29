import { Component, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { EventDTO } from '../models/eventDTO.model';
import { EventDATA } from '../models/eventDATA.model';

const EVENT_URL = 'https://localhost:8443/api/event/';
@Injectable({ providedIn: 'root' })
export class EventService {
  constructor(private httpClient: HttpClient) { }

  getEvents(page : number | string): Observable<Event[]> {
    return this.httpClient
      .get<Event[]>(EVENT_URL+"?page="+page)
      .pipe(catchError(error => this.handleError(error)));
  }

  getSingleEvent(id: number | string): Observable<Event> {
    return this.httpClient
      .get<Event>(EVENT_URL + id)
      .pipe(catchError(error => this.handleError(error)));
  }

  getSingleEventData(id: number | string): Observable<EventDATA> {
    return this.httpClient
      .get<EventDATA>(EVENT_URL +"DATA/"+ id)
      .pipe(catchError(error => this.handleError(error)));
  }

  likeEvent(id:number | string){
    return this.httpClient
      .post(EVENT_URL +"like/"+ id,  
      { 
        withCredentials: true 
      }).pipe(catchError(error => this.handleError(error)));
  }

  putEvent(id:number|string, event:EventDTO){
    return this.httpClient.put(EVENT_URL+id,event)
    .pipe(catchError(error => this.handleError(error)));
  }

  postEvent(event:EventDTO){
    return this.httpClient.post(EVENT_URL,event)
    .pipe(catchError(error => this.handleError(error)));
  }

  deleteEvent(id: number): Observable<{}> {
    return this.httpClient
      .delete(EVENT_URL + id)
      .pipe(catchError(error => this.handleError(error)));
  }

  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return Observable.throw(
      'Server error (' + error.status + '): ' + error.message
    );
  }

}
