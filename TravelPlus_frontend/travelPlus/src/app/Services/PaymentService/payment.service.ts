import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private apiUrl = 'http://localhost:8080/api/v1/payments';

  constructor(private http: HttpClient) { }

  // Method to get payment by reservation ID
  getPaymentByReservationId(reservationId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${reservationId}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
