import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraphsTableComponent } from './graphs-table.component';

describe('GraphsTableComponent', () => {
  let component: GraphsTableComponent;
  let fixture: ComponentFixture<GraphsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GraphsTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GraphsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
