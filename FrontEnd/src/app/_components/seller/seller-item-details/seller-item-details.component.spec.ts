import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerItemDetailsComponent } from './seller-item-details.component';

describe('SellerItemDetailsComponent', () => {
  let component: SellerItemDetailsComponent;
  let fixture: ComponentFixture<SellerItemDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SellerItemDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SellerItemDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
