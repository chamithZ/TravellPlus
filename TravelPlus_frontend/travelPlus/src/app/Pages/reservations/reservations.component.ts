import { Component, OnInit } from '@angular/core';
import { ReservationService } from '../../Services/ReservationService/reservation.service';
import { Reservation } from '../../Models/Reservation';
import { ReservationRoomTypeService } from '../../Services/ReservationRoomType/reservation-room-type.service';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {
  reservations: any[] = [];

  constructor(
    private reservationService: ReservationService,
    private reservationRoomTypeService: ReservationRoomTypeService // Inject ReservationRoomTypeService
  ) { }

  ngOnInit(): void {
    this.getReservationsByUserId();
  }

  getReservationsByUserId(): void {
    const userId = localStorage.getItem('userId');
    if (userId !== null) {
      this.reservationService.getReservationsByUserId(parseInt(userId))
        .subscribe((response: any) => {
          if (response && response.content) {
            this.reservations = response.content;
            console.log('Reservations:', this.reservations);
            this.getRoomTypesForReservations(); // Call method to fetch room types
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

  getRoomTypesForReservations(): void {
  
    this.reservations.forEach(reservation => {
      
      this.reservationRoomTypeService.getReservationRoomType(reservation.reservationId)
        .subscribe((response: any) => {
          if (response && response.content) {
            // Assuming response.content is an array of room types for the reservation
            reservation.roomTypes = response.content; // Assign room types to reservation
            console.log(response.content)
          } else {
            console.error('Invalid response format:', response);
          }
        }, error => {
          console.error('Error fetching room types for reservation:', error);
        });
    });
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
