import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-hotel-card',
  templateUrl: './hotel-card.component.html',
  styleUrl: './hotel-card.component.css'
})
export class HotelCardComponent implements OnInit {
  data: any[] = [];
  loading: boolean = true;
  error: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<any[]>('/hotels').subscribe(
      (response) => {
        this.data = response;
        this.loading = false;
      },
      (error) => {
        this.error = error;
        this.loading = false;
      }
    );
  }
}