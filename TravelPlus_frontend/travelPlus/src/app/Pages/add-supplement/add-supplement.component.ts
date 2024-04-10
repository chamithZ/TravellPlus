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
  styleUrl: './add-supplement.component.css'
})
export class AddSupplementComponent {

  constructor(private router:Router,private http: HttpClient, private formBuilder: FormBuilder, private supplementService:SupplementService,  private route: ActivatedRoute ) {}
  contractId!: number;
  seasons: Season[] = [];
  supplementForm!: FormGroup;

  ngOnInit(): void {
  
   
    this.route.params.subscribe(params => 
      this.contractId = params['contractId']);
    this.fetchSeasons(this.contractId);
    
    // Initialize the form
    this.supplementForm = this.formBuilder.group({
      serviceName: ['', Validators.required],
      contractId: [this.contractId, Validators.required], // Initialize contractId in the form
      supplementSeason: this.formBuilder.array([]) // Initialize an empty form array for roomTypeSeasons
    });
    
  }
  get supplementSeason(): FormArray {
    return this.supplementForm.get('supplementSeason') as FormArray;
  }
  
  addSeasonFormGroup(season: Season): void {
    // Create a FormGroup for the season
    const seasonFormGroup = this.formBuilder.group({
      seasonId: [season.seasonId],
      price: ['', Validators.required]
    });
  
    // Add the FormGroup to the roomTypeSeasons FormArray
    this.supplementSeason.push(seasonFormGroup);
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

  onSubmit(): void {
    // Get the form value
    const formValue = this.supplementForm.value;
    
    const modifiedSupplementForm = formValue.supplementSeason.map((season: any) => ({
      ...season,
      season: { seasonId: season.seasonId }
    }));
    
    const transformedValue = {
      ...formValue,
      supplementSeason: modifiedSupplementForm 
    };

    
    console.log(transformedValue); 
    this.supplementService.addSupplements(transformedValue as Supplement[]).subscribe((res)=>{
      this.supplementForm.reset({ contractId: this.contractId });
      this.router.navigate(['/addSupplement',this.contractId]);
   
  })
}

}
