import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchItemListComponent } from './search-item-list.component';

describe('SearchItemListComponent', () => {
  let component: SearchItemListComponent;
  let fixture: ComponentFixture<SearchItemListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchItemListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
