import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Tutorial } from '../models/tutorial.model';

const baseUrl = 'http://localhost:8080/api/tutorials';

@Injectable({
  providedIn: 'root'
})
export class TutorialService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Tutorial[]> {
    //return this.http.get<Tutorial[]>(baseUrl);
    return of([{id:'1'}])
  }

  get(id: any): Observable<Tutorial> {
    //return this.http.get<Tutorial>(`${baseUrl}/${id}`);
    return of({id:'1'})
  }

  create(data: any): Observable<any> {
    //return this.http.post(baseUrl, data);
    return of({id:'1'})
  }

  update(id: any, data: any): Observable<any> {
    //return this.http.put(`${baseUrl}/${id}`, data);
    return of({id:'1'})
  }

  delete(id: any): Observable<any> {
    //return this.http.delete(`${baseUrl}/${id}`);
    return of({id:'1'})
  }

  deleteAll(): Observable<any> {
    //return this.http.delete(baseUrl);
    return of({id:'1'})
  }

  findByTitle(title: any): Observable<Tutorial[]> {
    //return this.http.get<Tutorial[]>(`${baseUrl}?title=${title}`);
    return of([{id:'1'}])
  }
}