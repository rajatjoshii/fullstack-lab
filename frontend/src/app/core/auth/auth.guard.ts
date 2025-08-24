import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthStore } from './auth.store';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private store: AuthStore, private router: Router) {}

  canActivate(): boolean {
    let token = this.store.token;
    if (!token) {
      const stored = sessionStorage.getItem('token');
      if (stored) {
        this.store.setToken(stored);
        token = stored;
      }
    }
    if (token) {
      return true;
    }
    this.router.navigate(['/auth/login']);
    return false;
  }
}
