import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { contract } from '../Models/Contract';
import { Response } from '../Models/Response';

@Injectable({
  providedIn: 'root'
})
export class ContractService {
  baseUrl="http://localhost:8080/api/v1"

  constructor(private http:HttpClient) { }

  httpOptions={
    headers:new HttpHeaders({'Content-Type':'application/json'})
  }
  addContract(contract:contract){
    return this.http.post<Response<contract>>(`${this.baseUrl}/contract`,contract,this.httpOptions)
  }
}
