import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorServerConnectionComponent } from './error-server-connection.component';

describe('ErrorServerConnectionComponent', () => {
  let component: ErrorServerConnectionComponent;
  let fixture: ComponentFixture<ErrorServerConnectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ErrorServerConnectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErrorServerConnectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
