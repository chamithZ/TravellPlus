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
  showPasswordMismatchError: boolean = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    this.userForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      contactNo: ['', Validators.required],
      nic: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required], // Add confirm password field
      userType: ['guest'],
      age: ['', Validators.required],
    }, {
      validator: this.passwordMatchValidator // Add custom validator
    });
  }

  // Custom validator to check if passwords match
  passwordMatchValidator(formGroup: FormGroup) {
    const passwordControl = formGroup.get('password');
    const confirmPasswordControl = formGroup.get('confirmPassword');

    if (passwordControl && confirmPasswordControl) {
      const password = passwordControl.value;
      const confirmPassword = confirmPasswordControl.value;

      if (password !== confirmPassword) {
        confirmPasswordControl.setErrors({ passwordMismatch: true });
      } else {
        confirmPasswordControl.setErrors(null);
      }
    }
  }

  onSubmit() {
    if (this.userForm.valid) {
      const userData = this.userForm.value;
      console.log(userData);
      this.authService.register(userData).subscribe(
        response => {
          console.log('User registered successfully:', response.content);
          // Redirect the user to the login page
          this.router.navigate(['/login']);
        },
        error => {
          console.error('Error occurred during user registration:', error);
        }
      );
    } else {
      // Check if confirm password field is empty
      const confirmPasswordControl = this.userForm.get('confirmPassword');
      if (confirmPasswordControl && confirmPasswordControl.errors?.['required']) {

        confirmPasswordControl.setErrors({ required: true });
      }
  
      // Check if password mismatch error exists
      if (this.userForm.hasError('passwordMismatch')) {
        this.showPasswordMismatchError = true;
      } else {
        this.showPasswordMismatchError = false;
      }
    }

    
  }
}
