import { Component } from '@angular/core';

@Component({
  selector: 'app-home-search-bar',
  templateUrl: './home-search-bar.component.html',
  styleUrl: './home-search-bar.component.css'
})
export class HomeSearchBarComponent {
  city: string = '';
  checkInDate: string = '';
  checkOutDate: string = '';

  currentDate: string = new Date().toISOString().split('T')[0];
  tomorrowDate: string = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().split('T')[0];
}
