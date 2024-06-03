import { Injectable , Output, EventEmitter} from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { User } from '../../Models/User';
import { Response } from '../../Models/Response';
import { tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private baseUrl = 'http://localhost:8080/api/v1'; 
  private loginUrl = 'http://localhost:8080/api/v1/auth/login'; 
  @Output() getLogStatus: EventEmitter<any> = new EventEmitter();

  constructor(private http: HttpClient) { }

  register(user: User): Observable<Response<User>> {
    const registerUrl = `${this.baseUrl}/auth/signup`;
    return this.http.post<Response<User>>(registerUrl, user);
  }
  login(credentials: { email: string, password: string }): Observable<{ token: string }> {
    // Send a POST request to the login endpoint with the provided credentials
    return this.http.post<{ token: string }>(this.loginUrl, credentials)
      .pipe(
        tap((response: { token: string }) => {
          // Emit the user's full name or 'Sign In' based on the response
          if (response.token) {
            this.getLogStatus.emit('logged');
          } else {
            this.getLogStatus.emit('Sign In');
          }
        })
      );
  }
  isAuthorize(user:String): boolean {
    const userType = localStorage.getItem('userType');
    return userType === user;
  }
  setToken(token: string): void {
    localStorage.setItem('token', token); // Store the token in local storage
  }

  isLogged(): Observable<boolean> {
    // Retrieve the token from local storage
    const token = this.getToken();
    
    
    // Return an Observable that emits true if token exists, false otherwise
    return of(!!token);
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
    this.getLogStatus.emit('Sign In');
  }
}
function jwt_decode(token: string): any {
  throw new Error('Function not implemented.');
}

