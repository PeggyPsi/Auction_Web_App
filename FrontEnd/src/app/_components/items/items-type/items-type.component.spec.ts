import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsTypeComponent } from './items-type.component';

describe('ItemsTypeComponent', () => {
  let component: ItemsTypeComponent;
  let fixture: ComponentFixture<ItemsTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemsTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemsTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
