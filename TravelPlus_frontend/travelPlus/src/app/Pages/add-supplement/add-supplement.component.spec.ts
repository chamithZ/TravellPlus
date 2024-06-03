import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { FormArray, FormBuilder } from '@angular/forms';
import { of } from 'rxjs';
import { AddSupplementComponent } from './add-supplement.component';
import { SupplementService } from '../../Services/SupplementService/supplement.service';
import { Season } from '../../Models/Season';

describe('AddSupplementComponent', () => {
  let component: AddSupplementComponent;
  let fixture: ComponentFixture<AddSupplementComponent>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockSupplementService: jasmine.SpyObj<SupplementService>;
  let route: ActivatedRoute;

  beforeEach(async () => {
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
    mockSupplementService = jasmine.createSpyObj('SupplementService', ['addSupplements']);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AddSupplementComponent],
      providers: [
        FormBuilder,
        { provide: Router, useValue: mockRouter },
        { provide: SupplementService, useValue: mockSupplementService },
        { provide: ActivatedRoute, useValue: { params: of({ contractId: 1 }) } }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSupplementComponent);
    component = fixture.componentInstance;
    route = TestBed.inject(ActivatedRoute);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  mockSupplementService = jasmine.createSpyObj('SupplementService', ['addSupplements', 'fetchSeasons']);

  it('should add season form group on addSeasonFormGroup', () => {
    component.addSupplementFormGroup(); // Add supplement form group first
    const season: Season = { seasonId: 1, name: 'Summer' } as unknown as Season; // Mock season data
    component.addSeasonFormGroup(0, season);
    const supplementSeasonsArray = component.supplements.at(0).get('supplementSeasons') as FormArray; // Cast to FormArray
    if (supplementSeasonsArray) {
      expect(supplementSeasonsArray.length).toBe(1); // Ensure season form group is added to supplementSeasons
      const seasonIdControl = supplementSeasonsArray.at(0).get('seasonId');
      if (seasonIdControl) {
        expect(seasonIdControl.value).toBe(season.seasonId); // Ensure seasonId control is added with correct value
      }
    }
  });

  it('should submit form and navigate on onSubmit', () => {
    spyOn(component['supplementService'], 'addSupplements').and.returnValue(of(null)); // Mock addSupplements method
    component.onSubmit();
    expect(mockSupplementService.addSupplements).toHaveBeenCalled(); // Ensure addSupplements is called
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/addSupplement', 1]); // Ensure navigation is called with correct route
    expect(component.supplementForm.getRawValue()).toEqual({ supplements: [] }); // Ensure form is reset
  });
});
