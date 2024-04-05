import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Hotel } from '../../Models/Hotel';

@Injectable({
  providedIn: 'root'
})
export class HotelDataService {
  private hotelsSubject = new BehaviorSubject<Hotel[]>([]);
  public hotels$ = this.hotelsSubject.asObservable();

  constructor() { }

  setHotels(hotels: Hotel[]) { // Change the parameter type to Hotel[]
    this.hotelsSubject.next(hotels);
  }
}
