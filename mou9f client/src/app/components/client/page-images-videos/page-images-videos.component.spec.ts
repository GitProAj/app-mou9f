import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageImagesVideosComponent } from './page-images-videos.component';

describe('PageImagesVideosComponent', () => {
  let component: PageImagesVideosComponent;
  let fixture: ComponentFixture<PageImagesVideosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PageImagesVideosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageImagesVideosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
