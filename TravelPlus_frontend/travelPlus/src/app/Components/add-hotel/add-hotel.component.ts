import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';

@Component({
  selector: 'app-add-hotel',
  templateUrl: './add-hotel.component.html',
  styleUrl: './add-hotel.component.css'
})
export class AddHotelComponent implements OnInit{

  hotelForm=this.fb.group({
    hotelName:[''],
    hotelAddress:[''],
    hotelCity:[''],
    hotelEmail:[''],
    hotelContactNo:['']
  })

  isDataUploaded=false;

  constructor(
    private fb:FormBuilder,
    private hotelService:HotelService
  ){}

  ngOnInit(): void { }

  get f(){
    return this.hotelForm.controls;
  }

  OnSubmit(){
    const values=this.hotelForm.value as Hotel;
    this.hotelService.addHotelComponent(values as Hotel).subscribe((res)=>{
      this.hotelForm.reset()
    })
    this.isDataUploaded=true;
  }

}


