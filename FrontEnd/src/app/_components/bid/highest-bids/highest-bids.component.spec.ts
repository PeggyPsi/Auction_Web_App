import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HighestBidsComponent } from './highest-bids.component';

describe('HighestBidsComponent', () => {
  let component: HighestBidsComponent;
  let fixture: ComponentFixture<HighestBidsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HighestBidsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HighestBidsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
