import { Component, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

const EVENT_URL = 'https://localhost:8443/api/event/';

@Injectable({ providedIn: 'root' })
export class AdminService {
  constructor(private httpClient: HttpClient) {}

  getEvents(): Observable<Event[]> {
    return this.httpClient
      .get<Event[]>(EVENT_URL)
      .pipe(catchError(this.handleError));
  }

  deleteEvent(id: number): Observable<{}> {
    return this.httpClient
      .delete(EVENT_URL + '/' + id)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.error(error);
    return Observable.throw(
      'Server error (' + error.status + '): ' + error.message
    );
  }
  
}
