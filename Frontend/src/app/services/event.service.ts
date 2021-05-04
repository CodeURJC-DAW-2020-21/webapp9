import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { EventDTO } from '../models/eventDTO.model';
import { EventDATA } from '../models/eventDATA.model';
import { Events } from '../models/event.model';
import { EventSRC } from '../models/eventSRC.model';

const EVENT_URL = '/api/event/';
@Injectable({ providedIn: 'root' })
export class EventService {
  constructor(private httpClient: HttpClient) { }

  getEvents(page : number | string): Observable<Events[]> {
    return this.httpClient
      .get<Events[]>(EVENT_URL+"?page="+page)
      .pipe(catchError((error: any) => this.handleError(error)));
  }

  getEvensSRC(id:number|string): Observable<EventSRC>{
    return this.httpClient.get<EventSRC>(EVENT_URL+"src/"+id)
    .pipe(catchError((error: any) => this.handleError(error)));

  }

  getSingleEvent(id: number | string): Observable<Events> {
    return this.httpClient
      .get<Events>(EVENT_URL + id)
      .pipe(catchError(error => this.handleError(error)));
  }

  getSingleEventData(id: number | string): Observable<EventDATA> {
    return this.httpClient
      .get<EventDATA>(EVENT_URL + "DATA/" + id)
      .pipe(catchError(error => this.handleError(error)));
  }

  likeEvent(id:number | string){
    return this.httpClient
      .post(EVENT_URL +"like/"+ id,  
      { 
        withCredentials: true 
      }).pipe(catchError((error: any) => this.handleError(error)));
  }

  postImage(name:string, type:number | string,file:File){
    let formdata: FormData = new FormData();
    formdata.append('file', file);
    console.log("FormData: ",file,type,name,EVENT_URL+"image/"+name+"/"+type)
    return this.httpClient.post(EVENT_URL+"image/"+name+"/"+type,formdata)
    .pipe(catchError((error: any) => this.handleError(error)));
  }

  putEvent(id:number|string, event:EventDTO){
    return this.httpClient.put(EVENT_URL+id,event)
    .pipe(catchError((error: any) => this.handleError(error)));
  }

  postEvent(event:EventDTO){
    return this.httpClient.post(EVENT_URL,event)
    .pipe(catchError((error: any) => this.handleError(error)));
  }

  deleteEvent(id: number) {
    return this.httpClient
      .delete(EVENT_URL + id)
      .pipe(catchError((error: any) => this.handleError(error)));
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(
      'Server error (' + error.status + '): ' + error.message
    );
  }

}
