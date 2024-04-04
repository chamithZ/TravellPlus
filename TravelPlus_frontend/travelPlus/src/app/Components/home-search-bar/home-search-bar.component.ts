import { Component, HostListener, Renderer2, ElementRef } from '@angular/core';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-home-search-bar',
  templateUrl: './home-search-bar.component.html',
  styleUrls: ['./home-search-bar.component.css']
})
export class HomeSearchBarComponent {
  city: string = '';
  checkInDate: string = '';
  checkOutDate: string = '';
  roomCount: number = 1; // Default to 1 room
  guestCount: number = 1; // Default to 1 guest
  dropdownOpen: boolean = false;
  hotels: Hotel[] = []; // To store the fetched hotel list

  currentDate: string = new Date().toISOString().split('T')[0];
  tomorrowDate: string = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().split('T')[0];

  constructor(private router: Router,private http: HttpClient, private formBuilder: FormBuilder,private hotelService: HotelService, private renderer: Renderer2, private el: ElementRef) {}

  ngOnInit() {
    this.toggleDropdown();
    this.renderer.listen('document', 'click', (event: MouseEvent) => {
      this.handleDocumentClick(event);
    });
  }

  handleDocumentClick(event: MouseEvent) {
    if (!this.el.nativeElement.contains(event.target)) {
      this.dropdownOpen = false;
    }
  }
 
  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  incrementRoomCount() {
    // Allow incrementing without a maximum limit
    this.roomCount++;
  }

  decrementRoomCount() {
    // Ensure room count is never less than 1
    if (this.roomCount > 1) {
      this.roomCount--;
    }
  }

  incrementGuestCount() {
    // Allow incrementing without a maximum limit
    this.guestCount++;
  }

  decrementGuestCount() {
    // Ensure guest count is never less than 1
    if (this.guestCount > 1) {
      this.guestCount--;
    }
  }

  searchHotels() {
    // Check if the required fields are set
    if (!this.city || !this.checkInDate || !this.checkOutDate) {
      console.error('Please fill in all required fields (Destination, Check-In Date, Check-Out Date)');

      return; // Exit the method if any of the required fields are not set
    }
  
    // Calling the searchHotel method from the hotel service
    this.hotelService.searchHotel(this.city, this.checkInDate, this.checkOutDate, this.guestCount, this.roomCount)
      .subscribe(response => {
        this.router.navigate(['/searchResult'], { queryParams: { hotels: JSON.stringify(response.content) } });
        
      });
  }
  
}
