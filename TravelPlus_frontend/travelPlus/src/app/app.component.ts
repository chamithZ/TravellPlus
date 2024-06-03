import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title(title: any) {
    throw new Error('Method not implemented.');
  }
  constructor(private router: Router) {}
  routesWithoutHeaderAndFooter:String[] = ['/addHotel', '/adminDashboard', '/hotelAdminDashboard', '/addContract/:hotelId','/error','/updateHotel/:hotelId','/updateContract/:contractId','/contractList','/hotelList','/addRoomType/:contractId','/addSupplement/:contractId','/addHotelAdmin','/disabledContracts','/HotelAdminDashboard','/hotelReservations/:hotelId','/adminList'];

  showAdminHeader(): boolean {
    return this.routesWithoutHeaderAndFooter.includes(this.router.url);
  }

  showRegularHeader(): boolean {
    return !this.routesWithoutHeaderAndFooter.includes(this.router.url);
  }
}
