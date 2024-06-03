import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewHotelAdminsComponent } from './view-hotel-admins.component';

describe('ViewHotelAdminsComponent', () => {
  let component: ViewHotelAdminsComponent;
  let fixture: ComponentFixture<ViewHotelAdminsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewHotelAdminsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewHotelAdminsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
