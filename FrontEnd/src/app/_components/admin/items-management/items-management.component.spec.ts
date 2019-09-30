import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsManagementComponent } from './items-management.component';

describe('ItemsManagementComponent', () => {
  let component: ItemsManagementComponent;
  let fixture: ComponentFixture<ItemsManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemsManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemsManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
