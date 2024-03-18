import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface Season {
  seasonId: number;
  seasonType: string;
  startDate: string;
  endDate: string;
  markUp: number;
  contractId: number;
}

@Component({
  selector: 'app-add-room-type',
  templateUrl: './add-room-type.component.html',
  styleUrls: ['./add-room-type.component.css']
})
export class AddRoomTypeComponent  {
  
  }


 

