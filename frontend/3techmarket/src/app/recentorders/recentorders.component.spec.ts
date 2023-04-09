import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentordersComponent } from './recentorders.component';

describe('RecentordersComponent', () => {
  let component: RecentordersComponent;
  let fixture: ComponentFixture<RecentordersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecentordersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentordersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
