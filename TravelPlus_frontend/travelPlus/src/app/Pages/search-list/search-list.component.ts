import { Component } from '@angular/core';
import { Hotel } from '../../Models/Hotel';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search-list',
  templateUrl: './search-list.component.html',
  styleUrl: './search-list.component.css'
})
export class SearchListComponent {

  hotels: Hotel[] = [];

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    // Retrieve the hotel list from the query parameters
    this.route.queryParams.subscribe(params => {
      if (params['hotels']) {
        this.hotels = JSON.parse(params['hotels']);
        console.log(this.hotels)
      }
    });
  }

}
