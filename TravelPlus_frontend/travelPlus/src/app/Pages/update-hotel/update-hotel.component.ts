import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Hotel } from '../../Models/Hotel';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Response } from '../../Models/Response';

@Component({
  selector: 'app-update-hotel',
  templateUrl: './update-hotel.component.html',
  styleUrls: ['./update-hotel.component.css']
})
export class UpdateHotelComponent implements OnInit {
  hotelForm!: FormGroup;
  hotelId!: number;
  isDataUploaded = false;
  hotel!: Hotel;

  constructor(
    private fb: FormBuilder,
    private hotelService: HotelService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.hotelId = params['hotelId'];
      this.hotelService.getHotelById(this.hotelId).subscribe((response: Response<Hotel>) => {
        this.hotel = response?.content;
        console.log(this.hotel.hotelName);

        this.initializeForm();
      });
    });
  }

  // Method to initialize the hotelForm FormGroup
  initializeForm(): void {
    this.hotelForm = this.fb.group({
      hotelName: [this.hotel.hotelName],
      hotelAddress: [this.hotel.hotelAddress],
      hotelCity: [this.hotel.hotelCity],
      hotelEmail: [this.hotel.hotelEmail],
      hotelContactNo: [this.hotel.hotelContactNo],
      hotelDescription: [this.hotel.hotelDescription]
    });
  }

  get f() {
    return this.hotelForm.controls;
  }

  onSubmit() {
    const values = this.hotelForm.value as Hotel;
    values.hotelId = this.hotelId; // Set the hotelId to the updated value
    this.hotelService.updateHotelComponent(values).subscribe((res) => {
      this.isDataUploaded = true;
      console.log("done")
    });
  }
}
