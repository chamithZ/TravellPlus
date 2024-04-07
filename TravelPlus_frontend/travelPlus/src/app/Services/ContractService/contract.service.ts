import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { contract } from '../../Models/Contract';
import { Response } from '../../Models/Response';

@Injectable({
  providedIn: 'root'
})
export class ContractService {
  baseUrl="http://localhost:8080/api/v1";

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  addContract(contract: contract): Observable<Response<contract>> {
    return this.http.post<Response<contract>>(`${this.baseUrl}/contracts`, contract, this.httpOptions);
  }

  getContractIdByHotelIdAndDateRange(hotelId: number, startDate: string, endDate: string): Observable<Response<number>> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);

    return this.http.get<Response<number>>(`${this.baseUrl}/contracts/${hotelId}`, { params: params });
  }
}
