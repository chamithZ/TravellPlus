import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../Models/User';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly apiUrl = 'http://localhost:8080/api/v1/users';
  constructor(private http:HttpClient) { }

  getUserByUserId(userId: number): Observable<User> {
    const url = `${this.apiUrl}/${userId}`; 
    return this.http.get<User>(url);
  }

  
  getUserByUser_id(userId: number): Observable<User> {
    const url = `${this.apiUrl}/userId/${userId}`; 
    return this.http.get<User>(url);
  }
  getAllAdmins(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/admins`);
  }
  updateUser(user: User): Observable<User> {
    const url = `${this.apiUrl}`;
    return this.http.put<User>(url, user);
  }
  deleteUser(userId:number):Observable<any>{
    const url = `${this.apiUrl}/${userId}`;
    return this.http.delete(url); 
  }
}
