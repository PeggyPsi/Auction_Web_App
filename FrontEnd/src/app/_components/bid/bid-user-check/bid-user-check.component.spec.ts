import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BidUserCheckComponent } from './bid-user-check.component';

describe('BidUserCheckComponent', () => {
  let component: BidUserCheckComponent;
  let fixture: ComponentFixture<BidUserCheckComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BidUserCheckComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BidUserCheckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
