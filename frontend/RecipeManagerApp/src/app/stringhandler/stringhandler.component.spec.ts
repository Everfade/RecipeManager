import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StringhandlerComponent } from './stringhandler.component';

describe('StringhandlerComponent', () => {
  let component: StringhandlerComponent;
  let fixture: ComponentFixture<StringhandlerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StringhandlerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StringhandlerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
