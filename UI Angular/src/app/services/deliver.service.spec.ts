import { TestBed } from '@angular/core/testing';

import { DeliverService } from './deliver.service';

describe('DeliverService', () => {
  let service: DeliverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeliverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
