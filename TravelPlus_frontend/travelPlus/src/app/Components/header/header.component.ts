import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  mobileMenuOpen: boolean = false;
  isLoggedIn: boolean = false; // Track user's login status

  constructor(private router: Router) { }

  ngOnInit(): void {
    // Check if there is a token in local storage
    const token = localStorage.getItem('token');
    if (token) {
      this.isLoggedIn = true; // User is logged in
    }
  }

  toggleMobileMenu() {
    this.mobileMenuOpen = !this.mobileMenuOpen;
  }

  logout() {
    // Clear token from local storage and navigate to login page
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    this.router.navigate(['/login']);
  }
}
