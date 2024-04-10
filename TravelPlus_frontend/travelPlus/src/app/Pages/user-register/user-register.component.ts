import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../Services/AuthService/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent {
  userForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService,private router: Router) {
    this.userForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      contactNo: ['', Validators.required],
      nic: ['', Validators.required],
      password: ['', Validators.required],
      userType: ['guest'],
      age: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      const userData = this.userForm.value;
      this.authService.register(userData).subscribe(
        response => {
          console.log('User registered successfully:', response.content);
          // Redirect the user to the login page
          this.router.navigate(['/login']);
        },
        error => {
          console.error('Error occurred during user registration:', error);
          // Optionally, display an error message to the user
        }
      );
    } else {
      // Form is invalid, display error messages or handle it as needed
    }
}
}
