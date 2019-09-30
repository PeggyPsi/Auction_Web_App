import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerItemEditComponent } from './seller-item-edit.component';

describe('SellerItemEditComponent', () => {
  let component: SellerItemEditComponent;
  let fixture: ComponentFixture<SellerItemEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SellerItemEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SellerItemEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
