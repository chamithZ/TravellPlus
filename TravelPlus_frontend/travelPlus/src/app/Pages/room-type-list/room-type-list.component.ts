import { Component, OnInit } from '@angular/core';
import { RoomService } from '../../Services/RoomService/room.service';
import { ContractService } from '../../Services/ContractService/contract.service';
import { RoomType } from '../../Models/RoomType';
import { ActivatedRoute, Route } from '@angular/router';
import { Response } from '../../Models/Response';

@Component({
  selector: 'app-room-type-list',
  templateUrl: './room-type-list.component.html',
  styleUrls: ['./room-type-list.component.css']
})
export class RoomTypeListComponent implements OnInit {
  roomTypes: any[] = [];
  hotelId!: number;
  loadingRoomTypes: boolean = true; // Initialize loadingRoomTypes as true

  constructor(
    private roomTypeService: RoomService,
    private contractService: ContractService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.hotelId = parseInt(this.route.snapshot.paramMap.get('hotelId') || '');
    
    const today = new Date().toISOString().slice(0, 10);
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const tomorrowStr = tomorrow.toISOString().slice(0, 10);

    this.contractService.getContractIdByHotelIdAndDateRange(this.hotelId, today, tomorrowStr).subscribe(contract => {
      this.roomTypeService.getAvailableRoomTypes(contract.content, today, tomorrowStr).subscribe((response) => {
        if (response && Array.isArray(response)) {
      
            response.forEach(roomType => {
              // Here, you can perform any operations you want on each roomType
              // For example, you can push each roomType to the roomTypes array
              this.roomTypes.push(roomType);
            });
            console.log(this.roomTypes); // Now, roomTypes should contain all the roomType objects
          
        }
        this.loadingRoomTypes = false; // Set loadingRoomTypes to false when room types are loaded
      });
    });
  }
}
