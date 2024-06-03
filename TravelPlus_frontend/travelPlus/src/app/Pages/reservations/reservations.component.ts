import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../Services/ReservationService/reservation.service';
import { Reservation } from '../../Models/Reservation';
import { ReservationRoomTypeService } from '../../Services/ReservationRoomType/reservation-room-type.service';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';
import { HotelImages } from '../../Models/HotelImages';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {
  reservations: any[] = [];
  hotel:Hotel[]=[];
  hotelImages: HotelImages[]=[];
  loading: boolean = false; 

  constructor(
    private reservationService: ReservationService,
    private reservationRoomTypeService: ReservationRoomTypeService, // Inject ReservationRoomTypeServic
    private hotelService: HotelService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Please login first!',
        confirmButtonText: 'OK'
      }).then((result) => {
        if (result.isConfirmed) {
          this.router.navigate(['/login']); 
        }
      });
    } else {
      this.getReservationsByUserId();
    }
  }
  

  getReservationsByUserId(): void {
    this.loading = true;
    const userId = localStorage.getItem('userId');
    if (userId !== null) {
      this.reservationService.getReservationsByUserId(parseInt(userId))
        .subscribe((response: any) => {
          if (response && response.content) {
            // Filter out reservations with reservationStatus set to false
            this.reservations = response.content.filter((reservation: any) => reservation.reservationStatus === true);
            console.log('Reservations:', this.reservations);
            this.getRoomTypesAndHotelsForReservations(); // Call method to fetch room types
          } else {
            console.error('Invalid response format:', response);
          }
        }, error => {
          console.error('Error fetching reservations:', error);
        });
    } else {
      console.error('User ID is null');
    }
  }
  
  getRoomTypesAndHotelsForReservations(): void {
    this.reservations.forEach(reservation => {
      // Fetch room types for the reservation
      this.reservationRoomTypeService.getReservationRoomType(reservation.reservationId)
        .subscribe((response: any) => {
          if (response && response.content) {
            reservation.roomTypes = response.content; // Assign room types to reservation
            console.log('Room types:', response.content);

            // Fetch hotel for the reservation
            this.hotelService.getHotelById(reservation.hotelId)
              .subscribe((hotelResponse: any) => {
                if (hotelResponse && hotelResponse.content) {
                  const hotel = hotelResponse.content;
                  this.hotel.push(hotel); // Store hotel in the hotels array
                  console.log('Hotel:', hotel);
                  this.loading = false;
                } else {
                  console.error('Invalid hotel response format:', hotelResponse);
                }
              }, hotelError => {
                console.error('Error fetching hotel:', hotelError);
              });
          } else {
            console.error('Invalid response format:', response);
          }
        }, error => {
          console.error('Error fetching room types for reservation:', error);
        });
    });
  }

  viewReservation(reservationId: number): void {
    this.router.navigate(['/reservationOverview', reservationId]);
  }
  getHotelName(hotelId: number): string {
    const hotel = this.hotel.find(h => h.hotelId === hotelId);
    return hotel ? hotel.hotelName : 'Unknown Hotel';
  }
  getHotelImage(hotelId: number): HotelImages | null {
    const hotel = this.hotel.find(h => h.hotelId === hotelId);
    return hotel && hotel.hotelImages.length > 0 ? hotel.hotelImages[0] : null;
  }
  

  getElapsedTime(reservedDate: Date): string {
    const now = new Date();
    const differenceMs = now.getTime() - new Date(reservedDate).getTime();
    const seconds = Math.floor(differenceMs / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);

    if (days > 0) {
      return `${days} day${days > 1 ? 's' : ''} ago`;
    } else if (hours > 0) {
      return `${hours} hour${hours > 1 ? 's' : ''} ago`;
    } else if (minutes > 0) {
      return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
    } else {
      return `${seconds} second${seconds > 1 ? 's' : ''} ago`;
    }
  }
  
}
