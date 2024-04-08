import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms'; // Import FormBuilder and FormGroup
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
  roomTypeForm!: FormGroup; // Declare roomTypeForm as a FormGroup

  constructor(private router: Router,private http: HttpClient, private formBuilder: FormBuilder, private roomService:RoomService, private route: ActivatedRoute  ) {} // Inject FormBuilder

  ngOnInit(): void {
    this.route.params.subscribe(params => 
      this.contractId = params['contractId']);
    this.fetchSeasons(this.contractId);

    // Initialize the form
    this.roomTypeForm = this.formBuilder.group({
      roomTypeName: ['', Validators.required],
      roomSize: ['', Validators.required],
      roomBedType: ['', Validators.required],
      roomDescription: ['', Validators.required],
      roomImage: ['', Validators.required],
      contractId: [this.contractId, Validators.required], // Initialize contractId in the form
      roomTypeSeasons: this.formBuilder.array([]) // Initialize an empty form array for roomTypeSeasons
    });
    
  }

  fetchSeasons(contractId: number): void {
    const url = `http://localhost:8080/api/v1/seasons/${contractId}`;
  
    this.http.get<any>(url)
      .subscribe((data: any) => {
        if (data && data.content) {
          this.seasons = data.content;
          console.log('Seasons:', this.seasons);
  
          // Iterate over seasons to add controls to roomTypeSeasons FormArray
          this.seasons.forEach(season => {
            this.addSeasonFormGroup(season);
          });
        } else {
          console.error('Invalid response format:', data);
        }
      }, error => {
        console.error('Error fetching seasons:', error);
      });
  }

  // Getter for roomTypeSeasons form array
  get roomTypeSeasons(): FormArray {
    return this.roomTypeForm.get('roomTypeSeasons') as FormArray;
  }

  addSeasonFormGroup(season: Season): void {
    // Create a FormGroup for the season
    const seasonFormGroup = this.formBuilder.group({
      seasonId: [season.seasonId],
      roomPrice: ['', Validators.required], // Add validators if needed
      noOfRooms: ['', Validators.required]  // Add validators if needed
    });
  
    // Add the FormGroup to the roomTypeSeasons FormArray
    this.roomTypeSeasons.push(seasonFormGroup);
  }

  
  // Method to handle form submission
  onSubmit(): void {
    // Get the form value
    const formValue = this.roomTypeForm.value;
    
    const modifiedRoomTypeSeasons = formValue.roomTypeSeasons.map((season: any) => ({
      ...season,
      season: { seasonId: season.seasonId }
    }));
    
    const transformedValue = {
      ...formValue,
      roomTypeSeasons: modifiedRoomTypeSeasons
    };

    
    console.log(transformedValue); 
    this.roomService.addRoomType(transformedValue as RoomType).subscribe((res)=>{
      this.roomTypeForm.reset({ contractId: this.contractId });
      this.router.navigate(['/addSupplement',this.contractId]);
  })
   
}
}