import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventGraphsComponent } from './event-graphs.component';

describe('EventGraphsComponent', () => {
  let component: EventGraphsComponent;
  let fixture: ComponentFixture<EventGraphsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventGraphsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EventGraphsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
