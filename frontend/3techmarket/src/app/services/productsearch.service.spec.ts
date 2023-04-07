import { TestBed } from '@angular/core/testing';

import { ProductsearchService } from './productsearch.service';

describe('ProductsearchService', () => {
  let service: ProductsearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductsearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
