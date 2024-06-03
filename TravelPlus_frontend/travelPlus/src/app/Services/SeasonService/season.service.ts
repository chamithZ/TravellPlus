import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Season } from '../../Models/Season';
import { Response } from '../../Models/Response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SeasonService {

  baseUrl = "http://localhost:8080/api/v1";

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getSeasons(contractId: number) {
    return this.http.get<Season[]>(`${this.baseUrl}/seasons/${contractId}`, this.httpOptions);
  }

  
  getSeasonForReservation(contractId: number,checkIn :String,checkOut:String): Observable<Response<Season>> {
    const url = `${this.baseUrl}/seasons/${contractId}/${checkIn}/${checkOut}`;
    return this.http.get<Response<Season>>(url);
  }
}
