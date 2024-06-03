import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Hotel } from '../../Models/Hotel';
import { Response } from '../../Models/Response';
import { Observable } from 'rxjs';
import { AuthService } from '../AuthService/auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  baseUrl = "http://localhost:8080/api/v1";

  constructor(private http: HttpClient,private authService: AuthService) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  addHotelComponent(hotel: Hotel): Observable<Response<Hotel>> {
    const headers = this.authService.getTokenHeader();
    return this.http.post<Response<Hotel>>(`${this.baseUrl}/hotels`, hotel, { headers });

  }

  
  updateHotelComponent(hotel: Hotel): Observable<Response<Hotel>> {
    const headers = this.authService.getTokenHeader();
    return this.http.put<Response<Hotel>>(`${this.baseUrl}/hotels`, hotel, { headers });

  }

  deleteHotel(hotelId: number): Observable<Response<any>> {
    const headers = this.authService.getTokenHeader();
    const url = `${this.baseUrl}/hotels/${hotelId}`;
    
    return this.http.delete<Response<any>>(url, { headers });
  }

  searchHotel(destination: string, checkIn: string, checkOut: string, guestCount: number, numberOfRooms: number, page: number, size: number): Observable<Response<Hotel[]>> {
    let params = new HttpParams()
      .set('destination', destination)
      .set('checkIn', checkIn)
      .set('checkOut', checkOut)
      .set('guestCount', guestCount.toString())
      .set('numberOfRooms', numberOfRooms.toString())
      .set('page', page.toString()) // Include page parameter
      .set('size', size.toString()); // Include size parameter

    const url = `${this.baseUrl}/hotels/search`;
    const options = { params };

    return this.http.get<Response<Hotel[]>>(url, options);
  }

  getHotelById(hotelId: number): Observable<Response<Hotel>> {
    const url = `${this.baseUrl}/hotels/${hotelId}`;
    return this.http.get<Response<Hotel>>(url);
  }

  getAllHotels(page: number, pageSize: number): Observable<any> {
    const url = `${this.baseUrl}/hotels?page=${page}&pageSize=${pageSize}`;
    return this.http.get<any>(url);
  }
}
