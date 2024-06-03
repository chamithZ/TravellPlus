import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../Services/AuthService/auth-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-hotel-admin-register',
  templateUrl: './hotel-admin-register.component.html',
  styleUrls: ['./hotel-admin-register.component.css']
})
export class HotelAdminRegisterComponent {
  userForm!: FormGroup;
  hotelId!: string;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router,  private route: ActivatedRoute) {

  }
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.hotelId = params['hotelId'];
      this.initializeForm();
    });
  }

  
  initializeForm() {
    this.userForm = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      contactNo: ['', Validators.required],
      nic: ['', Validators.required],
      password: ['', Validators.required],
      retypePassword: ['', Validators.required],
      userType: ['hotelAdmin'],
      userStatus:true,
      age: ['', Validators.required],
      propertyId: [this.hotelId, Validators.required], // Assign hotelId to propertyId
    }, { validator: this.passwordMatchValidator });
  }
  onSubmit() {
    if (this.userForm.valid) {
      const userData = this.userForm.value;
      this.authService.register(userData).subscribe(
        response => {
          // Show success message using SweetAlert
          Swal.fire({
            icon: 'success',
            title: 'Admin Added Successful',
            text: 'Hotel admin Added successfully!',
          }).then(() => {
            // Redirect the user to the login page
            this.router.navigate(['/adminDashboard']);
          });
        },
        error => {
          console.error('Error occurred during user registration:', error);
          // Show error message using SweetAlert
          Swal.fire({
            icon: 'error',
            title: 'Registration Failed',
            text: 'An error occurred during registration. Please try again later.',
          });
        }
      );
    } else {
      if (this.userForm.errors && this.userForm.errors['passwordMismatch']) {
        // Show alert for password mismatch
        Swal.fire({
          icon: 'error',
          title: 'Password Mismatch',
          text: 'The password and retype password do not match.',
        });
      } 
      console.log("hehe")
    }
  }

  passwordMatchValidator(formGroup: FormGroup) {
    const passwordControl = formGroup.get('password');
    const retypePasswordControl = formGroup.get('retypePassword');

    if (passwordControl && retypePasswordControl && passwordControl.value !== retypePasswordControl.value) {
      if (retypePasswordControl) {
        retypePasswordControl.setErrors({ passwordMismatch: true });
      }
    } else {
      if (retypePasswordControl) {
        retypePasswordControl.setErrors(null);
      }
    }
  }
}
