import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BidderHomeComponent } from './bidder-home.component';

describe('BidderHomeComponent', () => {
  let component: BidderHomeComponent;
  let fixture: ComponentFixture<BidderHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BidderHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BidderHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
