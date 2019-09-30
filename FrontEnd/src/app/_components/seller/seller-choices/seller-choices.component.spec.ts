import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SellerChoicesComponent } from './seller-choices.component';

describe('SellerChoicesComponent', () => {
  let component: SellerChoicesComponent;
  let fixture: ComponentFixture<SellerChoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SellerChoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SellerChoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
