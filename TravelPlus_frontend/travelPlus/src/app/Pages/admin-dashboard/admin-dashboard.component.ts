import { Component } from '@angular/core';
import { AuthService } from '../../Services/AuthService/auth-service.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {
  constructor(private authService:AuthService,private router:Router){}

  ngOnInit(): void {
    if (!this.authService.isAuthorize("admin")) {
      this.router.navigate(['/error']); // Redirect to error component if user is not admin
    }
  }
  selectedOption: string = 'hotel'; 
}
