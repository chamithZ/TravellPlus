import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SeasonServiceService {

  baseUrl="http://localhost:8080/api/v1"

  constructor(private http:HttpClient) { }
  
httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json'})
}


}
