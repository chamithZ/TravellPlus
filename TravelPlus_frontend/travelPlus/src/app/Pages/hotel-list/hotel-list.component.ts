import { Component } from '@angular/core';
import { ContractService } from '../../Services/ContractService/contract.service';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { HotelDataService } from '../../Services/HotelDataService/hotel-data.service';
import { Hotel } from '../../Models/Hotel';
import { Response } from '../../Models/Response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css']
})
export class HotelListComponent {

  constructor(
    private hotelDataService: HotelDataService,
    private hotelService: HotelService,
    private contractService: ContractService,
    private router: Router // Inject Router service
  ) { }
  
  hotels: Hotel[] = [];

  ngOnInit(): void {
    this.hotelService.getAllHotels().subscribe((response: Response<Hotel[]>) => {
      this.hotels = response?.content;
      console.log('Hotel:', this.hotels);
    });
  }

  updateHotel(id: number): void {
    this.router.navigate(['/updateHotel', id]);
  }
  
  deleteHotel(id: number): void {
    this.hotelService.deleteHotel(id).subscribe((response: Response<any>) => {
      if (response.message == "success") {
        this.hotels = this.hotels.filter(hotel => hotel.hotelId !== id);
      } else {
        // Handle error or display error message
      }
    });
  }

  addContract(hotelId: number): void {
    this.router.navigate(['/addContract', hotelId]); // Redirect to addContract with hotel ID as parameter
  }
}
