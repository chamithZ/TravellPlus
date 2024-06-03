import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../Services/AuthService/auth-service.service'; // Import your authentication service

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  mobileMenuOpen: boolean = false;
  isLoggedIn: boolean = false; // Track user's login status

  constructor(private router: Router, private authService: AuthService) {
    authService.getLogStatus.subscribe(name => this.isLoggedIn = name !== 'Sign In');
   }

  ngOnInit(): void {
    this.authService.isLogged().subscribe((loggedIn: boolean) => {
      this.isLoggedIn = loggedIn;
    });
  }

  toggleMobileMenu() {
    this.mobileMenuOpen = !this.mobileMenuOpen;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  navigateToUserProfile(): void {
    // Navigate to the user profile route
    this.router.navigateByUrl('/account');
  }
}
