import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../Services/UserService/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {
  userForm!: FormGroup;
  userData: any; // Variable to store user data
  userId!: number;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    if (userId !== null) {
      this.userService.getUserByUser_id(parseInt(userId)).subscribe(
        (user) => {
          this.userData = user; // Store user data
          console.log(this.userData)
          this.initForm(); // Initialize the form after user data is fetched
        },
        (error) => {
          console.error('Error fetching user data:', error);
        }
      );
    } else {
      console.error('userId not found');
    }
  }

  initForm(): void {
    this.userForm = this.formBuilder.group({
      userId: [this.userData.content.userId],
      name: [this.userData.content.name, Validators.required],
      email: [this.userData.content.email, [Validators.required, Validators.email]],
      contactNo: [this.userData.content.contactNo, Validators.required],
      password:[this.userData.content.password,Validators.required],
      nic: [this.userData.content.nic, Validators.required],
      age: [this.userData.content.age, Validators.required],
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      const userData = this.userForm.value;
      // Call service method to update user
      this.userService.updateUser(userData).subscribe(
        (response) => {
          console.log('User updated successfully:', response);
          // Redirect or show success message
          this.router.navigate(['/profile']); // Example redirect to profile page
        },
        (error) => {
          console.error('Error occurred during user update:', error);
          // Handle error, show error message, etc.
        }
      );
    }
  }
}
