import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Season } from '../../Models/Season';
import { Supplement } from '../../Models/Supplement';
import { SupplementService } from '../../Services/SupplementService/supplement.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-supplement',
  templateUrl: './add-supplement.component.html',
  styleUrls: ['./add-supplement.component.css']
})
export class AddSupplementComponent {
  constructor(
    private router: Router,
    private http: HttpClient,
    private formBuilder: FormBuilder,
    private supplementService: SupplementService,
    private route: ActivatedRoute
  ) {}

  contractId!: number;
  seasons: Season[] = [];
  supplementForm!: FormGroup;

  ngOnInit(): void {
    this.route.params.subscribe(params => this.contractId = params['contractId']);
    this.fetchSeasons(this.contractId);
    
    this.supplementForm = this.formBuilder.group({
      supplements:this.formBuilder.array([])
    });
  }

  fetchSeasons(contractId: number): void {
    const url = `http://localhost:8080/api/v1/seasons/${contractId}`;
  
    this.http.get<any>(url).subscribe((data: any) => {
      if (data && data.content) {
        this.seasons = data.content;
        console.log(this.seasons)
      } else {
        console.error('Invalid response format:', data);
      }
    }, error => {
      console.error('Error fetching seasons:', error);
    });

  }
  get supplements(): FormArray {
    return this.supplementForm.get('supplements') as FormArray;
  }


  addSupplementFormGroup(): void {
    const supplementGroup = this.formBuilder.group({
      serviceName: ['', Validators.required],
      contractId: [this.contractId, Validators.required],
      supplementSeasons: this.formBuilder.array([])
    });

    
    this.supplements.push(supplementGroup);
    const supplementIndex = this.supplements.length - 1;
  
    // Add the room type seasons to the new roomTypeFormGroup
    this.seasons.forEach((season: Season) => {
      this.addSeasonFormGroup(supplementIndex, season);
    });
  }
  


  addSeasonFormGroup(supplementIndex: number, season: Season): void {
    // Create a FormGroup for the season
    const seasonFormGroup = this.formBuilder.group({
      seasonId: [season.seasonId],
      price: ['', Validators.required]
    });
  
    // Get the supplementSeasons FormArray for the specified supplement index
    const supplementSeasonsArray = this.supplements.at(supplementIndex).get('supplementSeasons') as FormArray;
  
    // Add the FormGroup to the supplementSeasons FormArray
    supplementSeasonsArray.push(seasonFormGroup);
  }
  
  onSubmit(): void {
    const formValue = this.supplementForm.value;
  
    const supplementValues =  formValue.supplements.map((supplement: any) => ({
        ...supplement,
        supplementSeason: supplement.supplementSeasons.map((season: any) => ({
          ...season,
          season: { seasonId: season.seasonId }
        }))
      }))
    
    console.log(supplementValues)
    this.supplementService.addSupplements(supplementValues as Supplement[]).subscribe(() => {
      this.supplementForm.reset({ contractId: this.contractId });
      this.router.navigate(['/adminDashboard']);
    });
  }
  
}
