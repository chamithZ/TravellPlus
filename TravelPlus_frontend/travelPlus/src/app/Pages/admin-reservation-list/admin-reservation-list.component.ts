import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Reservation } from '../../Models/Reservation';
import { ReservationService } from '../../Services/ReservationService/reservation.service';
import { Response } from '../../Models/Response';
import { Hotel } from '../../Models/Hotel';
import { ReservationRoomTypeService } from '../../Services/ReservationRoomType/reservation-room-type.service';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { User } from '../../Models/User';
import { UserService } from '../../Services/UserService/user.service';

@Component({
  selector: 'app-admin-reservation-list',
  templateUrl: './admin-reservation-list.component.html',
  styleUrls: ['./admin-reservation-list.component.css']
})
export class AdminReservationListComponent implements OnInit {

  hotelId!: number;
  reservations: any[] = [];
  hotel:Hotel[]=[];
  user:User[]=[];

  constructor(private route: ActivatedRoute, private reservationService: ReservationService, private router: Router,private reservationRoomTypeService:ReservationRoomTypeService,private hotelService:HotelService,private userService:UserService) { }

  ngOnInit(): void {
    this.hotelId = parseInt(this.route.snapshot.paramMap.get('hotelId') || '');
    this.loadReservations();
  
  }
  getRoomTypesAndHotelsForReservations(): void {

    this.reservations.forEach(reservation => {
      console.log("first")
      // Fetch room types for the reservation
      this.reservationRoomTypeService.getReservationRoomType(reservation.reservationId)
        .subscribe((response: any) => {
          console.log(reservation)
          if (response && response.content) {
            reservation.roomTypes = response.content; // Assign room types to reservation
            console.log('Room types:', response.content);

            // Fetch hotel for the reservation
            this.hotelService.getHotelById(this.hotelId)
              .subscribe((hotelResponse: any) => {
                if (hotelResponse && hotelResponse.content) {
                  const hotel = hotelResponse.content;
                  this.hotel.push(hotel); // Store hotel in the hotels array
                  console.log('Hotel:', hotel);
                } else {
                  console.error('Invalid hotel response format:', hotelResponse);
                }
              }, hotelError => {
                console.error('Error fetching hotel:', hotelError);
              });
          } else {
            console.error('Invalid response format:', response);
          }
          console.log(reservation.userId)
          this.userService.getUserByUserId(reservation.userId)
          .subscribe((userResponse: any) => {
            if (userResponse && userResponse.content) {
              const user = userResponse.content;
              console.log("hehe45")
              this.hotel.push(user); // Store hotel in the hotels array
              console.log('Hotel:', user);
            } else {
              console.error('Invalid hotel response format:', userResponse);
            }
          }, hotelError => {
            console.error('Error fetching hotel:', hotelError);
          });
          
        }, error => {
          console.error('Error fetching room types for reservation:', error);
        });
    });
  }
  getHotelName(hotelId: number): string {
    const hotel = this.hotel.find(h => h.hotelId === hotelId);
    return hotel ? hotel.hotelName : '';
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
  loadReservations(): void {

    this.reservationService.getReservationsByHotel(this.hotelId).subscribe(
      (response:any) => {

        console.log(response.content)
        if (1) {
          this.reservations = response;
          this.getRoomTypesAndHotelsForReservations();
        } else {
          console.error('Error fetching reservations:', response.message);
        }
      },
      error => {
        console.error('Error fetching reservations:', error);
      }
    
    );
  }

}
