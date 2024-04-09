import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../Models/User';
import { Response } from '../../Models/Response';


@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private baseUrl = 'http://localhost:8080/api/v1'; 
  private loginUrl = 'http://localhost:8080/api/v1/auth/login'; 

  constructor(private http: HttpClient) { }

  login(credentials: { email: string, password: string }): Observable<{ token: string }> {
    // Send a POST request to the login endpoint with the provided credentials
    return this.http.post<{ token: string }>(this.loginUrl, credentials);
  }

  setToken(token: string): void {
    localStorage.setItem('token', token); // Store the token in local storage
  }

  getToken(): string | null {
    return localStorage.getItem('token'); // Retrieve the token from local storage
  }

  getUserInfo(): { userId: string, userType: string } | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwt_decode(token); // Decode the JWT token
      return { userId: decodedToken.userId, userType: decodedToken.userType }; // Extract user information
    }
    return null;
  }   
  getTokenHeader(): HttpHeaders {
    // Retrieve the token from local storage
    const token = localStorage.getItem('token');
    
    // Check if token exists
    if (token) {
      // Include the token in the Authorization header
      return new HttpHeaders({ 
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      });
    } else {
      // If token does not exist, return empty headers or handle it accordingly
      return new HttpHeaders();
    }
  }

  
  getUserByEmail(email: String): Observable<Response<User>> {
    const url = `${this.baseUrl}/users/${email}`;
    return this.http.get<Response<User>>(url);
  }
 
  logout(): void {
    localStorage.removeItem('token'); // Remove the token from local storage on logout
  }
}
function jwt_decode(token: string): any {
  throw new Error('Function not implemented.');
}

