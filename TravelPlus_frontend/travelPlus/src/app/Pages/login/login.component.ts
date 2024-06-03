import { Component } from '@angular/core';
import { AuthService } from '../../Services/AuthService/auth-service.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from '../../Models/User';
import { Router,NavigationEnd } from '@angular/router';
import { HeaderComponent } from '../../Components/header/header.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  userId!: number;
  hotelId!: number;
  constructor(private authService: AuthService, private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }
  onSubmit(): void {
    console.log("hehe1")
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
  
        this.authService.getUserByEmail(credentials.email).subscribe({
          next: response => {
            const user: User = response.content;
            this.userId = user.userId;
            this.hotelId = user.propertyId;
            localStorage.setItem('userId', this.userId.toString());
            localStorage.setItem('userType', user.userType);
            
            // Move userType check here
            if (localStorage.getItem('userType') == "admin") {
              this.router.navigateByUrl('/adminDashboard');
            } else if (localStorage.getItem('userType') == "hotelAdmin") {
              const hotelId = this.hotelId;
              this.router.navigateByUrl('/HotelAdminDashboard', { state: { hotelId: hotelId } });
            } else if (localStorage.getItem('userType') == "guest") {
              this.router.navigateByUrl('/');
            }
          },
          error: err => {
            console.error('Get user error:', err);
          }
        });
      },
      error: err => {
        if (err.status === 403) {
          Swal.fire({
            icon: 'error',
            title: 'Invalid Credentials',
            text: 'Please check your email and password and try again.',
          });
        } else {
          console.error('Login error:', err);
        }
      }
    });
  }
}  