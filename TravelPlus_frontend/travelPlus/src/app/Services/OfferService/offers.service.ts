import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from '../../Models/Response';
import { Offer } from '../../Models/Offer';

@Injectable({
  providedIn: 'root'
})
export class OffersService {

  constructor(private http: HttpClient) { }

  getAllOffersForContract(contractId: number): Observable<Response<Offer[]>> {
    return this.http.get<Response<Offer[]>>(`http://localhost:8080/api/v1/offers/${contractId}`);
  }
}
