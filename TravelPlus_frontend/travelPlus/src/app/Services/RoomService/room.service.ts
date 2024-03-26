import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RoomType } from '../../Models/RoomType';
import { Response } from '../../Models/Response';

@Injectable({
  providedIn: 'root'
})
export class RoomService {
  baseUrl="http://localhost:8080/api/v1"
  constructor(private http:HttpClient) { }

  httpOptions={
    headers:new HttpHeaders({'Content-Type':'application/json'})
  }
  
  addRoomType(Room:RoomType){
   return this.http.post<Response<RoomType>>(`${this.baseUrl}/roomType`,Room,this.httpOptions)
  }
}
