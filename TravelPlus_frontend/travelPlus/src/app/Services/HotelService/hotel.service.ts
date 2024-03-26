import { HttpClient,HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Hotel } from '../../Models/Hotel';
import { Response } from '../../Models/Response';


@Injectable({
  providedIn: 'root'
})
export class HotelService {
  baseUrl="http://localhost:8080/api/v1"
  constructor(private http:HttpClient) { }

  httpOptions={
    headers:new HttpHeaders({'Content-Type':'application/json'})
  }
  
  addHotelComponent(hotel:Hotel){
   return this.http.post<Response<Hotel>>(`${this.baseUrl}/hotel`,hotel,this.httpOptions)
  }
}

