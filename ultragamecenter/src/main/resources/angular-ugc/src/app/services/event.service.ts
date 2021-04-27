import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Event } from '../models/event.model';

@Injectable({ providedIn: 'root' })
export class BooksService {
  constructor(private httpClient: HttpClient) { }
  getEvents(title: string) {
    return [
      
    ];
  }
}