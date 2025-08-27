import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthService, AuthError } from '../../core/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  error?: string;
  loading = false;

  form = this.fb.nonNullable.group({
    name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });

  constructor(private fb: FormBuilder, private auth: AuthService) {}

  submit() {
    if (this.form.invalid) return;
    this.error = undefined;
    this.loading = true;
    this.auth.register(this.form.getRawValue()).subscribe({
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
