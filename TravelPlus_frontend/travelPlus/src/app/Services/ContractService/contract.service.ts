import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { contract } from '../../Models/Contract';
import { Response } from '../../Models/Response';
import { AuthService } from '../AuthService/auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class ContractService {
  baseUrl="http://localhost:8080/api/v1";

  constructor(private http: HttpClient,private authService: AuthService) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  addContract(contract: contract): Observable<Response<contract>> {
    const headers = this.authService.getTokenHeader();
    return this.http.post<Response<contract>>(`${this.baseUrl}/contracts`, contract, { headers });
  }

  updateContract(contract: contract): Observable<Response<contract>> {
    return this.http.put<Response<contract>>(`${this.baseUrl}/contracts`, contract, this.httpOptions);
  }

  getContractIdByHotelIdAndDateRange(hotelId: number, startDate: string, endDate: string): Observable<Response<number>> {
    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);

    return this.http.get<Response<number>>(`${this.baseUrl}/contracts/${hotelId}`, { params: params });
  }

  getContractById(contractId: number): Observable<Response<contract>> {
    const url = `${this.baseUrl}/contracts/getContract/${contractId}`;
    return this.http.get<Response<contract>>(url);
  }

  deleteContract(contractId: number): Observable<Response<any>> {
    const url = `${this.baseUrl}/contracts/${contractId}`;
    return this.http.delete<Response<any>>(url);
  }

  enableContract(contractId: number): Observable<Response<any>> {
    const url = `${this.baseUrl}/contracts/${contractId}`;
    const headers = this.authService.getTokenHeader();
    return this.http.patch<Response<any>>(url, {}, { headers });
  }
  getAllContracts(page: number, pageSize: number,requestType: String): Observable<any> {
    const url = `${this.baseUrl}/contracts?page=${page}&pageSize=${pageSize}&requestType=${requestType}`;
    return this.http.get<any>(url);
  }
  getAllDisabledContracts(page: number, pageSize: number,requestType: String): Observable<any> {
    const url = `${this.baseUrl}/contracts?page=${page}&pageSize=${pageSize}&requestType=${requestType}`;
    return this.http.get<any>(url);
  }
}
