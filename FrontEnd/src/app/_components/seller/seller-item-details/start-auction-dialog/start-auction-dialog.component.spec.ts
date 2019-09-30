import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StartAuctionDialogComponent } from './start-auction-dialog.component';

describe('StartAuctionDialogComponent', () => {
  let component: StartAuctionDialogComponent;
  let fixture: ComponentFixture<StartAuctionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StartAuctionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StartAuctionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
