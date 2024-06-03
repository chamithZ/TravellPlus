import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Season } from '../../Models/Season';
import { RoomService } from '../../Services/RoomService/room.service';
import { RoomType } from '../../Models/RoomType';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
  selector: 'app-add-room-type',
  templateUrl: './add-room-type.component.html',
  styleUrls: ['./add-room-type.component.css']
})
export class AddRoomTypeComponent implements OnInit {
  seasons: Season[] = [];
  contractId!: number ;
  roomTypeForm!: FormGroup;

  constructor(private router: Router, private http: HttpClient, private formBuilder: FormBuilder, private roomService: RoomService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => this.contractId = params['contractId']);
    this.fetchSeasons(this.contractId);

    this.roomTypeForm = this.formBuilder.group({
      roomTypes: this.formBuilder.array([]) // Initialize an empty form array for roomTypes
    });
  }

  fetchSeasons(contractId: number): void {
    const url = `http://localhost:8080/api/v1/seasons/${contractId}`;
  
    this.http.get<any>(url)
      .subscribe((data: any) => {
        if (data && data.content) {
          this.seasons = data.content;
          console.log('Seasons:', this.seasons);
        } else {
          console.error('Invalid response format:', data);
        }
      }, error => {
        console.error('Error fetching seasons:', error);
      });
  }

  // Getter for roomTypes form array
  get roomTypes(): FormArray {
    return this.roomTypeForm.get('roomTypes') as FormArray;
  }

  addRoomTypeFormGroup(): void {
    const roomTypeFormGroup = this.formBuilder.group({
      roomTypeName: ['', Validators.required],
      roomSize: ['', Validators.required],
      roomBedType: ['', Validators.required],
      roomDescription: ['', Validators.required],
      roomImage: ['', Validators.required],
      contractId: [this.contractId, Validators.required],
      roomTypeSeasons: this.formBuilder.array([]) // Initialize empty roomTypeSeasons
    });
  
    this.roomTypes.push(roomTypeFormGroup);
  
    // Get the index of the newly added room type
    const roomTypeIndex = this.roomTypes.length - 1;
  
    // Add the room type seasons to the new roomTypeFormGroup
    this.seasons.forEach((season: Season) => {
      this.addSeasonFormGroup(roomTypeIndex, season);
    });
  }
  addSeasonFormGroup(roomTypeIndex: number, season: Season): void {
    // Create a FormGroup for the season
    const seasonFormGroup = this.formBuilder.group({
      seasonId: [season.seasonId],
      roomPrice: ['', Validators.required],
      noOfRooms: ['', Validators.required]
    });

    // Get the roomTypeSeasons FormArray for the specified room type index
    const roomTypeSeasonsArray = this.roomTypes.at(roomTypeIndex).get('roomTypeSeasons') as FormArray;

    // Add the FormGroup to the roomTypeSeasons FormArray
    roomTypeSeasonsArray.push(seasonFormGroup);
  }

  onSubmit(): void {
    // Get the form value
    const formValue = this.roomTypeForm.value;

    // Transform the form value to match the RoomType structure
    const roomTypes = formValue.roomTypes.map((roomType: any) => {
      const roomTypeSeasons = roomType.roomTypeSeasons.map((season: any) => ({
        ...season,
        season: { seasonId: season.seasonId }
      }));

      return {
        ...roomType,
        roomTypeSeasons
      };
    });
console.log(roomTypes)
    // Send the request to add multiple room types
    this.roomService.addRoomType(roomTypes as RoomType[]).subscribe(
      (res) => {
        console.log('Room types added successfully:', res);
        this.roomTypeForm.reset({ contractId: this.contractId });
        this.router.navigate(['/addSupplement', this.contractId]);
      },
      (error) => {
        console.error('Error adding room types:', error);
        // Handle error accordingly
      }
    );
  }
} 
  
