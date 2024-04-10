import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Supplement } from '../../Models/Supplement';
import { Response } from '../../Models/Response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SupplementService {

  baseUrl="http://localhost:8080/api/v1"
  constructor(private http:HttpClient) { }

  httpOptions={
    headers:new HttpHeaders({'Content-Type':'application/json'})
  }
  
  
  addSupplements(supplements: Supplement[]): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    return this.http.post<any>(`${this.baseUrl}/supplements`, supplements, httpOptions);
  }
  getSupplementList(contractId: number): Observable<Response<Supplement[]>> {
    return this.http.get<Response<Supplement[]>>(`${this.baseUrl}/supplements/${contractId}`, this.httpOptions);
  }
  
}
