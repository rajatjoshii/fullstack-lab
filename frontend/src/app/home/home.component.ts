import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth/auth.service';
import { AuthStore } from '../core/auth/auth.store';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user$ = this.store.user$;

  constructor(public auth: AuthService, private store: AuthStore) {}

  ngOnInit(): void {
    if (!this.store.user) {
      this.auth.getMe().subscribe();
    }
  }
}
