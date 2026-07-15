import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcheteComponent } from './achete.component';

describe('AcheteComponent', () => {
  let component: AcheteComponent;
  let fixture: ComponentFixture<AcheteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcheteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AcheteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
