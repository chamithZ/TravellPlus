import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Hotel } from '../../Models/Hotel';
import { Response } from '../../Models/Response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HotelService {
  baseUrl = "http://localhost:8080/api/v1";

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  addHotelComponent(hotel: Hotel): Observable<Response<Hotel>> {
    return this.http.post<Response<Hotel>>(`${this.baseUrl}/hotels`, hotel, this.httpOptions)
  }

  searchHotel(destination: string, checkIn: string, checkOut: string, guestCount: number, numberOfRooms: number): Observable<Response<Hotel[]>> {
    let params = new HttpParams()
      .set('destination', destination)
      .set('checkIn', checkIn)
      .set('checkOut', checkOut)
      .set('guestCount', guestCount.toString())
      .set('numberOfRooms', numberOfRooms.toString());

    const url = `${this.baseUrl}/hotels/search`;
    const options = { params };

    return this.http.get<Response<Hotel[]>>(url, options);
  }

  getHotelById(hotelId: number): Observable<Response<Hotel>> {
    const url = `${this.baseUrl}/hotels/${hotelId}`;
    return this.http.get<Response<Hotel>>(url);
  }
}
