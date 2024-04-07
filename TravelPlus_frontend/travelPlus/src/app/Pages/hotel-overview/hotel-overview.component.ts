import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';
import { Response } from '../../Models/Response';
import { SearchCriteria } from '../../Models/SearchCriteria';
import { ContractService } from '../../Services/ContractService/contract.service';
import { RoomService } from '../../Services/RoomService/room.service';
import { RoomType } from '../../Models/RoomType'; // Import RoomType model

@Component({
  selector: 'app-hotel-overview',
  templateUrl: './hotel-overview.component.html',
  styleUrls: ['./hotel-overview.component.css']
})
export class HotelOverviewComponent implements OnInit {
  hotel: Hotel | null = null;
  searchCriteria: SearchCriteria | null = null;
  roomTypes: RoomType[] = []; // Initialize roomTypes array

  constructor(
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private contractService: ContractService,
    private roomService: RoomService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const hotelId = +params['hotelId'];
      this.hotelService.getHotelById(hotelId).subscribe((response: Response<Hotel>) => {
        this.hotel = response.content;
        console.log('Hotel:', this.hotel);
      });

      // Fetch search criteria from query params
      this.route.queryParams.subscribe(queryParams => {
        if (queryParams && queryParams['searchCriteria']) {
          this.searchCriteria = JSON.parse(queryParams['searchCriteria']) as SearchCriteria;
          console.log('Search Criteria:', this.searchCriteria!);

          // Retrieve contract using search criteria
          if (this.searchCriteria) {
            this.contractService.getContractIdByHotelIdAndDateRange(hotelId, this.searchCriteria.checkInDate, this.searchCriteria.checkOutDate)
              .subscribe(contract => {
                console.log('Contract:', contract);
                // Handle contract data as needed

                // Ensure contract is not null before accessing its properties
                if (contract) {
                  this.roomService.getAvailableRoomTypes(contract.content, this.searchCriteria!.checkInDate, this.searchCriteria!.checkOutDate)
                    .subscribe(response => {
                      if (response && Array.isArray(response)) {
                        this.roomTypes = this.filterRoomTypesBySeason(response, this.searchCriteria!.checkInDate, this.searchCriteria!.checkOutDate);
                      } else {
                        console.log('Invalid response format:', response);
                      }
                    });
                } else {
                  console.log('Contract is null.');
                }
                
                
              });
          }
        }
      });
    });
  }

  
  // Helper method to filter room types based on season and set room price and count
  filterRoomTypesBySeason(roomTypes: RoomType[], checkInDate: string, checkOutDate: string): RoomType[] {
    
    const filteredRoomTypes: RoomType[] = [];
    roomTypes.forEach(roomType => {
      
      const validSeasons = roomType.roomTypeSeasons.filter(season => {
        return new Date(checkInDate) >= new Date(season.season.startDate) && new Date(checkOutDate) <= new Date(season.season.endDate);
      });
      if (validSeasons.length > 0) {
       
        const selectedSeason = validSeasons[0];
        roomType.roomPrice = selectedSeason.roomPrice;
        roomType.roomCount = selectedSeason.noOfRooms;
        filteredRoomTypes.push(roomType);
      }
    });
    return filteredRoomTypes;
  }
}
