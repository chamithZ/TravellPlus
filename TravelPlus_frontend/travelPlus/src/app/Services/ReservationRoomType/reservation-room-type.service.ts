import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReservationRoomType } from '../../Models/ReservationRoomType';
import { Response } from '../../Models/Response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReservationRoomTypeService {
  
  private baseUrl = 'http://localhost:8080/api/v1/reservationRooms/';

  constructor(private http: HttpClient) { }

  getReservationRoomType(reservationId: number): Observable<Response<ReservationRoomType>> {
    const url = `${this.baseUrl}${reservationId}`;
    return this.http.get<Response<ReservationRoomType>>(url);
  }
}
