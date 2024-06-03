import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplementListComponent } from './supplement-list.component';

describe('SupplementListComponent', () => {
  let component: SupplementListComponent;
  let fixture: ComponentFixture<SupplementListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SupplementListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SupplementListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
