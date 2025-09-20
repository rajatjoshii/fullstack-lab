import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UserMe } from '../../models';

@Injectable({ providedIn: 'root' })
export class AuthStore {
  private tokenSubject = new BehaviorSubject<string | null>(null);
  readonly token$ = this.tokenSubject.asObservable();

  //here we use dual access pattern returning value as an Observable and as .value non reactive

  private userSubject = new BehaviorSubject<UserMe | null>(null);
  readonly user$ = this.userSubject.asObservable(); // this is reactive mostly used in components 

  //in the constructor we again need to set the tokenSubject with the token during refresh which reinitialises the store 
  constructor() {
    const token = sessionStorage.getItem('token');
    if (token) {
      this.tokenSubject.next(token);
    }
  }

  get token(): string | null {
    return this.tokenSubject.value; // this is not reactive but works well for interceptors or routing - no need to subscribe
  }

  setToken(token: string) {
    this.tokenSubject.next(token);
    sessionStorage.setItem('token', token);
  }

  setUser(user: UserMe) {
    this.userSubject.next(user);
  }

  get user(): UserMe | null {
    return this.userSubject.value;
  }

  clear() {
    this.tokenSubject.next(null);
    this.userSubject.next(null);
    sessionStorage.removeItem('token');
  }
}
