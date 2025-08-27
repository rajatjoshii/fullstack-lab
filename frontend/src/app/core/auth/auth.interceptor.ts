import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthStore } from './auth.store';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store: AuthStore) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.store.token;
    const url = req.url;
    if (token && (url.startsWith('/api') || url.startsWith('/auth'))) {
      const authReq = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
      return next.handle(authReq);
    }
    return next.handle(req);
  }
}
