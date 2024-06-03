import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisabledContractListComponent } from './disabled-contract-list.component';

describe('DisabledContractListComponent', () => {
  let component: DisabledContractListComponent;
  let fixture: ComponentFixture<DisabledContractListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DisabledContractListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DisabledContractListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
