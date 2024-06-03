import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelAdminRegisterComponent } from './hotel-admin-register.component';

describe('HotelAdminRegisterComponent', () => {
  let component: HotelAdminRegisterComponent;
  let fixture: ComponentFixture<HotelAdminRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HotelAdminRegisterComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HotelAdminRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
