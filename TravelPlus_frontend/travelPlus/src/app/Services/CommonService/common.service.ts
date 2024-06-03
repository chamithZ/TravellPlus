import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Observer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  constructor(private http:HttpClient) { 

  }

  getLocation(): Observable<{ longitude: number; latitude: number; city?: string } | null> {
    return new Observable((observer: Observer<{ longitude: number; latitude: number; city?: string } | null>) => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const longitude = position.coords.longitude;
            const latitude = position.coords.latitude;
            console.log(longitude, latitude);
            const url = `https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${latitude}&longitude=${longitude}&localityLanguage=en`;
            this.http.get<any>(url).subscribe(
              (data) => {
                const city = data?.city || "Unknown";
                console.log(data)
                observer.next({ longitude, latitude, city });
                observer.complete();
              },
              (error) => {
                console.error("Error fetching location data:", error);
                observer.error(error);
              }
            );
          },
          (error) => {
            console.error("Error getting geolocation:", error);
            observer.error(error);
          }
        );
      } else {
        console.log("No support for geolocation");
        observer.next(null);
        observer.complete();
      }
    });
}
}
