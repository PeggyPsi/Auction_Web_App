import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatOutboxComponent } from './chat-outbox.component';

describe('ChatOutboxComponent', () => {
  let component: ChatOutboxComponent;
  let fixture: ComponentFixture<ChatOutboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChatOutboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChatOutboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
