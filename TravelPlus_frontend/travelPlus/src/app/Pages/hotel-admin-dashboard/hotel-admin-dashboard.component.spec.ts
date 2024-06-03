import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelAdminDashboardComponent } from './hotel-admin-dashboard.component';

describe('HotelAdminDashboardComponent', () => {
  let component: HotelAdminDashboardComponent;
  let fixture: ComponentFixture<HotelAdminDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HotelAdminDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HotelAdminDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
