import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService, AuthError } from '../../core/auth/auth.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  error?: string;
  loading = false;

  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(private fb: FormBuilder, private auth: AuthService, private http: HttpClient) {}

  submit() {
    if (this.form.invalid) return;
    this.error = undefined;
    this.loading = true;
    this.auth.login(this.form.getRawValue()).subscribe({
      next: () => (this.loading = false),
      error: (err: AuthError) => {
        this.loading = false;
        if (err.type === 'validation') {
          Object.entries(err.errors).forEach(([field, message]) => {
            this.form.get(field)?.setErrors({ server: message });
          });
        } else {
          this.error = err.message;
        }
      }
    });
  }
}
