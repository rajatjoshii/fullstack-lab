import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthStore } from './auth.store';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private store: AuthStore, private router: Router) {}

  canActivate(): boolean {
    let token = this.store.token;
    // line 12-18 are a fallback for page refresh where token in store are lost. Although auth store constructor reintialises them but angular does not ensure
    // that the store will run before the authguard. Also services are lazy and if not called during any component intialisation tree shaking will ignore them
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
