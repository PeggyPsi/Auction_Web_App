import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Image } from 'src/app/_models/item/images/image';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-image-carousel',
  templateUrl: './image-carousel.component.html',
  styleUrls: ['./image-carousel.component.css'],
  providers: [NgbCarouselConfig]
})
export class ImageCarouselComponent implements OnInit {
  @Input() images: Image[];
  loadImages = false;

  constructor( config: NgbCarouselConfig ) {
    config.showNavigationArrows=true;
    config.showNavigationIndicators=false;
    config.interval=0;
  }

  ngOnInit() {
    this.loadImages = true;
  }

}
