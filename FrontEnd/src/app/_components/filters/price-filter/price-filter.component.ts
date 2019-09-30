import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-price-filter',
  templateUrl: './price-filter.component.html',
  styleUrls: ['./price-filter.component.css']
})
export class PriceFilterComponent implements OnInit {
  
  @Output() onPricePicked: EventEmitter<any> = new EventEmitter<any>();

  priceRanges;
  loadPrices = false; 
  currentPriceRange; 

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
    this.currentPriceRange = "None";
    this.priceRanges = [
      {name: "Under 50 $", min: 0, max: 50},
      {name: "50-100 $", min: 50, max: 100},
      {name: "100-200 $", min: 100, max: 200},
      {name: "200-300 $", min: 200, max: 300},
      {name: "300-400 $", min: 300, max: 400},
      {name: "400-500 $", min: 400, max: 500},
      {name: "500-1000 $", min: 500, max: 1000},
      {name: "Over 1000 $", min: 1000, max: 0},
    ]
  }

  // User selected specific price of items
  onSelectPriceRange(price: any) {
    this.currentPriceRange = price.name;
    this.onPricePicked.emit({price: true, min: price.min, max: price.max});
  }

  onSelectNoPriceRange() {
    this.currentPriceRange = "None";  
    this.onPricePicked.emit({});
  }

}
