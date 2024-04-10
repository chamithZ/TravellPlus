import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoomType } from '../../Models/RoomType';
import { Response } from '../../Models/Response';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class RoomService {
  baseUrl = "http://localhost:8080/api/v1";

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  // Method to add a room type
  addRoomType(room: RoomType[]): Observable<Response<RoomType[]>> {
    return this.http.post<Response<RoomType[]>>(`${this.baseUrl}/roomTypes`, room, this.httpOptions);
  }

  // Method to get available room types
  getAvailableRoomTypes(contractId: number, checkInDate: string, checkOutDate: string): Observable<Response<RoomType[]>> {
    return this.http.get<Response<RoomType[]>>(`${this.baseUrl}/roomTypes/${contractId}/${checkInDate}/${checkOutDate}`);
  }
  getAvailableRoomCount(contractId: number, checkInDate: string, checkOutDate: string): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/roomTypes/availableRoomCount?contractId=${contractId}&checkInDate=${checkInDate}&checkOutDate=${checkOutDate}`);
  }
  
  
}
