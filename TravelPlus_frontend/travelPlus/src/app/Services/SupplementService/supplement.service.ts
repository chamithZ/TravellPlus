import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Supplement } from '../../Models/Supplement';
import { Response } from '../../Models/Response';

@Injectable({
  providedIn: 'root'
})
export class SupplementService {

  baseUrl="http://localhost:8080/api/v1"
  constructor(private http:HttpClient) { }

  httpOptions={
    headers:new HttpHeaders({'Content-Type':'application/json'})
  }
  
  addSupplement(supplement:Supplement){
   return this.http.post<Response<Supplement>>(`${this.baseUrl}/supplement`,supplement,this.httpOptions)
  }
}
