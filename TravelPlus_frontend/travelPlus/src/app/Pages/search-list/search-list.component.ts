import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Hotel } from '../../Models/Hotel';
import { HotelDataService } from '../../Services/HotelDataService/hotel-data.service';
import { SearchCriteria } from '../../Models/SearchCriteria';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ContractService } from '../../Services/ContractService/contract.service';
import { OffersService } from '../../Services/OfferService/offers.service';
import { Offer } from '../../Models/Offer';
import { RoomService } from '../../Services/RoomService/room.service';
import { RoomType } from '../../Models/RoomType';

@Component({
  selector: 'app-search-list',
  templateUrl: './search-list.component.html',
  styleUrls: ['./search-list.component.css']
})
export class SearchListComponent implements OnInit {
  hotels: Hotel[] = [];
  searchCriteria: SearchCriteria | null = null;
  city: string = '';
  offers:Offer[]=[];
  checkInDate: string = '';
  checkOutDate: string = '';
  roomCount: number = 1;
  guestCount: number = 1;
  dropdownOpen: boolean = false;
  currentDate: string = new Date().toISOString().split('T')[0]; // Set current date
  tomorrowDate: string = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().split('T')[0]; // Set tomorrow's date
  loading: boolean = false;
  currentPage: number = 0;
  totalPages: number = 0;
  pageSize: number = 10;

  constructor(private sanitizer: DomSanitizer,private router: Router, private route: ActivatedRoute, private hotelDataService: HotelDataService, private hotelService: HotelService, private contractService:ContractService,private offerService:OffersService,private roomService:RoomService) { }
  ngOnInit(): void {
    this.hotelDataService.hotels$.subscribe((hotels: Hotel[]) => {
      this.hotels = hotels;
      
      // Fetch offers for each hotel
      this.hotels.forEach(hotel => {
        this.fetchOffersForHotel(hotel);
      });
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
  
  fetchOffersForHotel(hotel: Hotel): void {
    // Fetch contract ID for the hotel
    this.contractService.getContractIdByHotelIdAndDateRange(hotel.hotelId, this.checkInDate, this.checkOutDate)
      .subscribe(contract => {
        if (contract && contract.content) {
          const contractId = contract.content;
  
          // Fetch available room types for the contract
          this.roomService.getAvailableRoomTypes(contractId, this.checkInDate, this.checkOutDate)
            .subscribe(roomTypesResponse => {
              if (roomTypesResponse && Array.isArray(roomTypesResponse)) {
                // Filter room types based on season
                const filteredRoomTypes = this.filterRoomTypesBySeason(roomTypesResponse, this.checkInDate, this.checkOutDate);
                
                // Assuming there is a single room type for simplicity, you can modify this logic as per your application
                if (filteredRoomTypes.length > 0) {
                  const roomType = filteredRoomTypes[0]; // Consider the first room type
                  if (roomType.roomPrice !== undefined) { // Add a check for undefined
                    hotel.pricePerNight = roomType.roomPrice; // Assign room price to the hotel
                  }
                }
              } else {
                console.log('Invalid room types response:', roomTypesResponse);
              }
            }, error => {
              console.error('Error fetching room types:', error);
            });
  
          // Fetch offers for the contract
          this.offerService.getAllOffersForContract(contractId)
            .subscribe(offersResponse => {
              if (offersResponse && Array.isArray(offersResponse.content)) {
                hotel.offers = offersResponse.content; // Assuming Hotel model has a property 'offers'
              } else {
                console.log('Invalid offer response format:', offersResponse);
              }
            }, error => {
              console.error('Error fetching offers:', error);
            });
        }
      });
  }

  calculateOfferPrice(roomPrice: number, discountPercentage: number): number {
    const discountAmount = (discountPercentage / 100) * roomPrice;
    return roomPrice - discountAmount;
  }
  
  filterRoomTypesBySeason(roomTypes: RoomType[], checkInDate: string, checkOutDate: string): RoomType[] {
    const filteredRoomTypes: RoomType[] = [];
    roomTypes.forEach(roomType => {
      const validSeasons = roomType.roomTypeSeasons.filter(season => {
        return new Date(checkInDate) >= new Date(season.season.startDate) && new Date(checkOutDate) <= new Date(season.season.endDate);
      });
      if (validSeasons.length > 0) {
        const selectedSeason = validSeasons[0];
        roomType.roomPrice = selectedSeason.roomPrice; // Assign room price
        filteredRoomTypes.push(roomType);
      }
    });
    return filteredRoomTypes;
  }
  
  navigateToHotelOverview(hotelId: number) {
    // Construct the query parameter with search criteria
    const queryParams = { searchCriteria: JSON.stringify(this.searchCriteria) };
    
    // Navigate to the hotel overview page with hotelId and searchCriteria as query parameters
    this.router.navigate(['/hotel', hotelId], { queryParams });
  }

  searchHotels() {
    this.loading = true; // Set loading to true when searching starts
  
    if (!this.city || !this.checkInDate || !this.checkOutDate) {
      console.error('Please fill in all required fields (Destination, Check-In Date, Check-Out Date)');
      this.loading = false; // Set loading to false when an error occurs
      return;
    }
  
    const searchCriteria: SearchCriteria = { // Create search criteria object
      city: this.city,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate,
      roomCount: this.roomCount,
      guestCount: this.guestCount
    };
    
    this.hotelService.searchHotel(this.city, this.checkInDate, this.checkOutDate, this.guestCount, this.roomCount, this.currentPage, this.pageSize)
      .subscribe(response => {
        this.hotelDataService.setHotels(response.content);
        this.router.navigate(['/searchResult'], { queryParams: { searchCriteria: JSON.stringify(searchCriteria) } });
        this.totalPages = Math.ceil(response.totalCount );
        this.loading = false; // Set loading to false when search is successful
      }, error => {
        console.error('Error searching hotels:', error); // Log any errors during search
        this.loading = false; // Set loading to false when an error occurs
      });
  }
  

  blobToUrl(blobData: any): string {
  try {
    const blob = new Blob([blobData], { type: 'image/jpg' }); // Adjust the MIME type if needed
    return URL.createObjectURL(blob);
  } catch (error) {
    console.error('Error creating object URL:', error);
    return ''; // Return empty string or handle the error appropriately
  }
}
getSafeImageUrl(imageData: Blob): SafeUrl {
  // Create a blob URL for the image data
  const imageUrl = URL.createObjectURL(imageData);
  // Ensure the URL is safe to use to prevent security vulnerabilities
  return this.sanitizer.bypassSecurityTrustUrl(imageUrl);
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
  nextPage() {
      this.currentPage++;
      this.searchHotels();

  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.searchHotels();
    }
  }
}
