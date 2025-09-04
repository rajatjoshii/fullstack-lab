import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, switchMap, tap, throwError } from 'rxjs';
import { AuthResponse, LoginRequest, RegisterRequest, UserMe } from '../../models';
import { AuthStore } from './auth.store';

export type AuthError =
  | { type: 'validation'; errors: Record<string, string>; }
  | { type: 'message'; message: string };

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(
    private http: HttpClient,
    private store: AuthStore,
    private router: Router
  ) {}

  register(data: RegisterRequest) {
    return this.http.post<AuthResponse>('api/auth/register', data).pipe(
      //tap(res => this.store.setToken(res.accessToken)),
      //switchMap(() => this.getMe()),
      tap(() => this.router.navigateByUrl('/login')),
      catchError(err => throwError(() => this.mapError(err)))
    );
  }

  login(data: LoginRequest) {
    return this.http.post<AuthResponse>('api/auth/login', data).pipe(
      tap(res => this.store.setToken(res.accessToken)),
      switchMap(() => this.getMe()),
      tap(() => this.router.navigateByUrl('/home')),
      catchError(err => throwError(() => this.mapError(err)))
    );
  }

  getMe() {
    return this.http.get<UserMe>('/api/users/me').pipe(
      tap(user => this.store.setUser(user)),
      catchError(err => throwError(() => this.mapError(err)))
    );
  }

  logout() {
    this.store.clear();
    this.router.navigateByUrl('api/auth/login');
  }

  private mapError(err: HttpErrorResponse): AuthError {
    if (err.status === 400 && err.error) {
      return { type: 'validation', errors: err.error };
    }
    if (err.status === 401 || err.status === 403) {
      return { type: 'message', message: 'Invalid credentials' };
    }
    return { type: 'message', message: 'Unexpected error' };
  }
}
