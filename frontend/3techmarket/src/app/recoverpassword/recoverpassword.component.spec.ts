import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecoverpasswordComponent } from './recoverpassword.component';

describe('RecoverpasswordComponent', () => {
  let component: RecoverpasswordComponent;
  let fixture: ComponentFixture<RecoverpasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecoverpasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecoverpasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
