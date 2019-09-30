import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BrowseItemListComponent } from './browse-item-list.component';

describe('BrowseItemListComponent', () => {
  let component: BrowseItemListComponent;
  let fixture: ComponentFixture<BrowseItemListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BrowseItemListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BrowseItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
