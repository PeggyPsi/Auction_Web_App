import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRatingDialogComponent } from './user-rating-dialog.component';

describe('UserRatingDialogComponent', () => {
  let component: UserRatingDialogComponent;
  let fixture: ComponentFixture<UserRatingDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserRatingDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserRatingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
