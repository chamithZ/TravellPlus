import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { AddHotelComponent } from './add-hotel.component';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Response } from '../../Models/Response';
import { Hotel } from '../../Models/Hotel';

describe('AddHotelComponent', () => {
  let component: AddHotelComponent;
  let fixture: ComponentFixture<AddHotelComponent>;
  let mockHotelService: jasmine.SpyObj<HotelService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    mockHotelService = jasmine.createSpyObj('HotelService', ['addHotelComponent']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [AddHotelComponent],
      providers: [
        FormBuilder,
        { provide: HotelService, useValue: mockHotelService },
        { provide: Router, useValue: mockRouter }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddHotelComponent);
    component = fixture.componentInstance;
    formBuilder = TestBed.inject(FormBuilder);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have invalid form when fields are empty', () => {
    expect(component.hotelForm.valid).toBeFalsy();
  });

  it('should have valid form when fields are filled correctly', () => {
    component.hotelForm.patchValue({
      hotelName: 'Test Hotel',
      hotelAddress: '123 Test St',
      hotelCity: 'Test City',
      hotelEmail: 'test@example.com',
      hotelContactNo: '1234567890',
      hotelDescription: 'Test description',
      hotelImages: []
    });
    expect(component.hotelForm.valid).toBeTruthy();
  });

  it('should add files to hotelImages array on file change', () => {
    const files = [{ name: 'test1.jpg' }, { name: 'test2.jpg' }];
    component.onFileChange({ target: { files } });
    expect(component.hotelForm.value.hotelImages?.length).toBe(2);
    expect((component.hotelForm.value.hotelImages?.[0] as { imageName: string }).imageName).toBe('test1.jpg');
    expect((component.hotelForm.value.hotelImages?.[1] as { imageName: string }).imageName).toBe('test2.jpg');
  });

  it('should submit form and navigate to addContract', () => { 
    const formValue = {
      hotelName: 'Test Hotel',
      hotelAddress: '123 Test St',
      hotelCity: 'Test City',
      hotelEmail: 'test@example.com',
      hotelContactNo: '1234567890',
      hotelDescription: 'Test description',
      hotelImages: []
    };
    component.hotelForm.reset();
  });
});
