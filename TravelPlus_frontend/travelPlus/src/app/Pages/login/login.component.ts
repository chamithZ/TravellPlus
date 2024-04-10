import { Component } from '@angular/core';
import { AuthService } from '../../Services/AuthService/auth-service.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from '../../Models/User';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  userId!: number;

  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    const credentials = {
      email: this.loginForm.get('email')?.value,
      password: this.loginForm.get('password')?.value
    };

    this.authService.login(credentials).subscribe({
      next: response => {
        const token = response.token;
        localStorage.setItem('token', token);
        console.log(token);

        // Redirect to home page after successful login
        this.router.navigateByUrl('/');
      },
      error: err => {
        // Handle login error
        console.error('Login error:', err);
      }
    });

    this.authService.getUserByEmail(credentials.email).subscribe({
      next: response => {
        const user: User = response.content;
        this.userId = user.userId;
        localStorage.setItem('userId', this.userId.toString());
        console.log(this.userId);
      },
      error: err => {
        // Handle get user error
        console.error('Get user error:', err);
      }
    });
  }
}
