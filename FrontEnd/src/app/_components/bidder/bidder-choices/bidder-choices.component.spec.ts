import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BidderChoicesComponent } from './bidder-choices.component';

describe('BidderChoicesComponent', () => {
  let component: BidderChoicesComponent;
  let fixture: ComponentFixture<BidderChoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BidderChoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BidderChoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
