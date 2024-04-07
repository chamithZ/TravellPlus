import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Hotel } from '../../Models/Hotel';
import { HotelDataService } from '../../Services/HotelDataService/hotel-data.service';
import { SearchCriteria } from '../../Models/SearchCriteria';
import { HotelService } from '../../Services/HotelService/hotel.service';

@Component({
  selector: 'app-search-list',
  templateUrl: './search-list.component.html',
  styleUrls: ['./search-list.component.css']
})
export class SearchListComponent implements OnInit {
  hotels: Hotel[] = [];
  searchCriteria: SearchCriteria | null = null;
  city: string = '';
  checkInDate: string = '';
  checkOutDate: string = '';
  roomCount: number = 1;
  guestCount: number = 1;
  dropdownOpen: boolean = false;
  currentDate: string = new Date().toISOString().split('T')[0]; // Set current date
  tomorrowDate: string = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().split('T')[0]; // Set tomorrow's date

  constructor(private router: Router, private route: ActivatedRoute, private hotelDataService: HotelDataService, private hotelService: HotelService) { }

  ngOnInit(): void {
    this.hotelDataService.hotels$.subscribe((hotels: Hotel[]) => {
      this.hotels = hotels;
    });
  
    this.route.queryParams.subscribe(params => {
      if (params && params['searchCriteria']) {
        this.searchCriteria = JSON.parse(params['searchCriteria']) as SearchCriteria;
  
        // Set form field values based on search criteria
        this.city = this.searchCriteria.city;
        this.checkInDate = this.searchCriteria.checkInDate;
        this.checkOutDate = this.searchCriteria.checkOutDate;
        this.roomCount = this.searchCriteria.roomCount;
        this.guestCount = this.searchCriteria.guestCount;
      }
    });
  }
  
  navigateToHotelOverview(hotelId: number) {
    // Construct the query parameter with search criteria
    const queryParams = { searchCriteria: JSON.stringify(this.searchCriteria) };
    
    // Navigate to the hotel overview page with hotelId and searchCriteria as query parameters
    this.router.navigate(['/hotel', hotelId], { queryParams });
  }

  searchHotels() {
    console.log('Search Criteria:', this.searchCriteria);
    if (!this.city || !this.checkInDate || !this.checkOutDate) {
      console.error('Please fill in all required fields (Destination, Check-In Date, Check-Out Date)');
      return;
    }
  
    const searchCriteria: SearchCriteria = { // Create search criteria object
      city: this.city,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate,
      roomCount: this.roomCount,
      guestCount: this.guestCount
    };
    this.hotelService.searchHotel(this.city, this.checkInDate, this.checkOutDate, this.guestCount, this.roomCount)
    .subscribe(response => {
      this.hotelDataService.setHotels(response.content);
      this.router.navigate(['/searchResult'], { queryParams: { searchCriteria: JSON.stringify(searchCriteria) } });;
    })
  }


  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  decrementRoomCount() {
    if (this.roomCount > 1) {
      this.roomCount--;
    }
  }

  incrementRoomCount() {
    this.roomCount++;
  }

  decrementGuestCount() {
    if (this.guestCount > 1) {
      this.guestCount--;
    }
  }

  incrementGuestCount() {
    this.guestCount++;
  }
}
