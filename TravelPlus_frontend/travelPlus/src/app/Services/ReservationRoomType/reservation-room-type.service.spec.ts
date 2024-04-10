import { TestBed } from '@angular/core/testing';

import { ReservationRoomTypeService } from './reservation-room-type.service';

describe('ReservationRoomTypeService', () => {
  let service: ReservationRoomTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservationRoomTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
