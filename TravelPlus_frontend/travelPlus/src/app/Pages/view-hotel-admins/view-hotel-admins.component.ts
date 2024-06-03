import { Component, OnInit } from '@angular/core';
import { UserService } from '../../Services/UserService/user.service';
import { User } from '../../Models/User';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';

@Component({
  selector: 'app-view-hotel-admins',
  templateUrl: './view-hotel-admins.component.html',
  styleUrls: ['./view-hotel-admins.component.css']
})
export class ViewHotelAdminsComponent implements OnInit {
  admins: User[] = [];
  hotels: { [key: number]: Hotel } = {}; // Object to store hotel details by hotelId

  constructor(private userService: UserService, private hotelService: HotelService) { }

  ngOnInit(): void {
    this.getAdmins();
  }

  getAdmins(): void {
    this.userService.getAllAdmins().subscribe((response: any) => {
      this.admins = response.content;
      // Extract hotelIds from admins and fetch hotel details for each id
      const hotelIds = this.admins.map(admin => admin.propertyId);
      this.getHotels(hotelIds);
    });
  }

  getHotels(hotelIds: number[]): void {
    for (const hotelId of hotelIds) {
      this.hotelService.getHotelById(hotelId).subscribe((response: any) => {
        this.hotels[hotelId] = response.content;
      });
    }
  }

  removeAdmin(adminId: number): void {
    this.userService.deleteUser(adminId).subscribe(() => {
      // Remove the deleted admin from the admins array
      this.admins = this.admins.filter(admin => admin.userId !== adminId);
      
    });
  }
}
