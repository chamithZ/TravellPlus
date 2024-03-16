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
export class AddRoomTypeComponent implements OnInit {
  roomTypeForm: FormGroup;
  seasons: Season[] = [];

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.roomTypeForm = this.fb.group({
      roomTypeName: ['', Validators.required],
      roomSize: ['', Validators.required],
      roomBedType: ['', Validators.required],
      roomDescription: ['', Validators.required],
      roomImage: ['', Validators.required],
      seasons: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.loadSeasonsFromAPI();
  }

  loadSeasonsFromAPI() {
    // Assuming API URL is 'seasonsEndpoint'
    this.http.get<any>('seasonsEndpoint').subscribe(response => {
      if (response.code === '006') {
        this.seasons = response.content;
        this.loadSeasonsForm();
      } else {
        // Handle error
      }
    });
  }

  loadSeasonsForm() {
    const seasonsFormArray = this.roomTypeForm.get('seasons') as FormArray;
    this.seasons.forEach(season => {
      seasonsFormArray.push(this.fb.group({
        seasonId: [season.seasonId],
        seasonType: [season.seasonType],
        startDate: [season.startDate],
        endDate: [season.endDate],
        markUp: [season.markUp],
        contractId: [season.contractId],
        roomDetails: this.fb.array([])
      }));
    });
  }

  getRoomDetailsControls(seasonIndex: number) {
    const seasonsFormArray = this.roomTypeForm.get('seasons') as FormArray;
    return (seasonsFormArray.at(seasonIndex).get('roomDetails') as FormArray).controls;
  }

  onSubmit() {
    if (this.roomTypeForm.valid) {
      // Process form submission
    } else {
      // Handle form validation errors
    }
  }

  addRoomDetails(seasonIndex: number) {
    const seasonsFormArray = this.roomTypeForm.get('seasons') as FormArray;
    const roomDetailsArray = seasonsFormArray.at(seasonIndex).get('roomDetails') as FormArray;
    roomDetailsArray.push(this.fb.group({
      roomPrice: ['', Validators.required],
      noOfRooms: ['', Validators.required]
    }));
  }

  removeRoomDetails(seasonIndex: number, roomIndex: number) {
    const seasonsFormArray = this.roomTypeForm.get('seasons') as FormArray;
    const roomDetailsArray = seasonsFormArray.at(seasonIndex).get('roomDetails') as FormArray;
    roomDetailsArray.removeAt(roomIndex);
  }
}
