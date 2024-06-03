import { Component, Renderer2, ElementRef } from '@angular/core';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Router } from '@angular/router';
import { HotelDataService } from '../../Services/HotelDataService/hotel-data.service';
import { SearchCriteria } from '../../Models/SearchCriteria'; // Import the SearchCriteria model
import { CommonService } from '../../Services/CommonService/common.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home-search-bar',
  templateUrl: './home-search-bar.component.html',
  styleUrls: ['./home-search-bar.component.css']
})
export class HomeSearchBarComponent {
  city: string = '';
  checkInDate: string = '';
  checkOutDate: string = '';
  roomCount: number = 1;
  guestCount: number = 1;
  dropdownOpen: boolean = false;
  location: any = '';
  loading: boolean = false; // Variable to track loading state

  currentDate: string = new Date().toISOString().split('T')[0];
  tomorrowDate: string = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().split('T')[0];
  maxDate: string = new Date(new Date().getFullYear() + 2, new Date().getMonth(), new Date().getDate()).toISOString().split('T')[0];

  constructor(private commonService: CommonService, private router: Router, private hotelDataService: HotelDataService, private renderer: Renderer2, private el: ElementRef, private hotelService: HotelService) {}

  ngOnInit() {
    this.commonService.getLocation().subscribe((data) => {
      this.location = data;
      console.log(this.location?.city);
    });

    this.renderer.listen('document', 'click', (event: MouseEvent) => {
      this.handleDocumentClick(event);
    });
  }

  handleDocumentClick(event: MouseEvent) {
    if (!this.el.nativeElement.contains(event.target)) {
      this.dropdownOpen = false;
    }
  }

  setLocation() {
    this.city = this.location?.city;
  }

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  incrementRoomCount() {
    this.roomCount++;
  }

  decrementRoomCount() {
    if (this.roomCount > 1) {
      this.roomCount--;
    }
  }

  incrementGuestCount() {
    this.guestCount++;
  }

  decrementGuestCount() {
    if (this.guestCount > 1) {
      this.guestCount--;
    }
  }

  searchHotels() {
    if (!this.city || !this.checkInDate || !this.checkOutDate) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Please fill in all required fields (Destination, Check-In Date, Check-Out Date)!',
      });
      return;
    }

    // Start loading
    this.loading = true;

    const searchCriteria: SearchCriteria = {
      city: this.city,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate,
      roomCount: this.roomCount,
      guestCount: this.guestCount
    };

    this.hotelService.searchHotel(this.city, this.checkInDate, this.checkOutDate, this.guestCount, this.roomCount, 0, 10)
      .subscribe(response => {
        this.hotelDataService.setHotels(response.content);
        this.router.navigate(['/searchResult'], { queryParams: { searchCriteria: JSON.stringify(searchCriteria) } });
      })
      .add(() => {
        // End loading
        this.loading = false;
      });
  }
}
