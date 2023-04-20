import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PricehistoryComponent } from './pricehistory.component';

describe('PricehistoryComponent', () => {
  let component: PricehistoryComponent;
  let fixture: ComponentFixture<PricehistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PricehistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PricehistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
