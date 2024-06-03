import { Component } from '@angular/core';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';
import { Response } from '../../Models/Response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hotel-list',
  templateUrl: './hotel-list.component.html',
  styleUrls: ['./hotel-list.component.css']
})
export class HotelListComponent {
  hotels: Hotel[] = [];
  currentPage = 0;
  pageSize = 10; // Set your desired page size
  totalPages = 0;
  
  constructor(
    private hotelService: HotelService,
    private router: Router // Inject Router service
  ) { }

  ngOnInit(): void {
    this.loadHotels();
  }

  loadHotels(): void {
    this.hotelService.getAllHotels(this.currentPage, this.pageSize)
      .subscribe((response: Response<Hotel[]>) => {
        this.hotels = response?.content;
        console.log('Hotel:', this.hotels);
        this.totalPages = Math.ceil(response.totalCount / this.pageSize);
      });
  }

  updateHotel(id: number): void {
    this.router.navigate(['/updateHotel', id]);
  }

  addHotelAdmin(hotelId: number) {
    // Navigate to the add admin page with the hotelId as a parameter
    this.router.navigate(['/addHotelAdmin'], { queryParams: { hotelId: hotelId } });
  }

  deleteHotel(id: number): void {
    this.hotelService.deleteHotel(id).subscribe((response: Response<any>) => {
      console.log("wd")
      console.log(response.message)
      if (response.message == "success") {
        this.hotels = this.hotels.filter(hotel => hotel.hotelId !== id);
      } else {
        // Handle error or display error message
      }
    });
  }
  getPageNumbers(): number[] {
    const pages = [];
    for (let i = 1; i <= this.totalPages; i++) {
      pages.push(i);
    }
    return pages;
  }

  addContract(hotelId: number): void {
    this.router.navigate(['/addContract', hotelId]); // Redirect to addContract with hotel ID as parameter
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadHotels();
  }
}
