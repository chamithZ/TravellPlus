import { Component } from '@angular/core';
import { AuthService } from '../../Services/AuthService/auth-service.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-hotel-admin-dashboard',
  templateUrl: './hotel-admin-dashboard.component.html',
  styleUrl: './hotel-admin-dashboard.component.css'
})
export class HotelAdminDashboardComponent {

  constructor(private authService:AuthService,private router:Router) { }
  selectedOption: string = '';
  hotelId!:Number;

  ngOnInit(): void {
  
      if (!this.authService.isAuthorize("hotelAdmin")) {
        this.router.navigate(['/error']); // Redirect to error component if user is not admin
      }else{
        const state = history.state;
        if (state && state.hotelId) {
          this.hotelId = state.hotelId;
         
        }
        console.log(this.hotelId)
      }
    
}
}