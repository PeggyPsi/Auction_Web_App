import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerItemListComponent } from './seller-item-list.component';

describe('SellerItemListComponent', () => {
  let component: SellerItemListComponent;
  let fixture: ComponentFixture<SellerItemListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SellerItemListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SellerItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
