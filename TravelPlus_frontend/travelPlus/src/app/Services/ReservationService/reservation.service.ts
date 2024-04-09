import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Response } from '../../Models/Response';
import { Reservation } from '../../Models/Reservation';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = 'http://localhost:8080/api/v1/reservations';

  constructor(private http: HttpClient) { }

 
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  addReservation(reservationData: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post<any>(this.apiUrl, reservationData, httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  getReservationsByUserId(userId: number): Observable<Response<Reservation[]>> {
    return this.http.get<Response<Reservation[]>>(`${this.apiUrl}/${userId}`)
      .pipe(
        catchError(this.handleError)
      );
  }
}
