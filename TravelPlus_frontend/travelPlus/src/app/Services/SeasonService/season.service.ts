import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Season } from '../../Models/Season';

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
}
