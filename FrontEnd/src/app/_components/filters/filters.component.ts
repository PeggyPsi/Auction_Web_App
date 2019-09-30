import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css']
})
export class FiltersComponent implements OnInit {

  query = {};
  categories = {};
  price = {};

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  // Picked category for searching
  categoryPicked(categoryInfo: any) {
    console.log("Category picked");
    this.categories = categoryInfo;
    console.log(categoryInfo);
  }

  // Price picked for searching
  pricePicked(price: any) {
    console.log("Price picked");
    this.price = price;
    console.log(this.price);
  }

  // Search
  filterSearch() {
    this.query = {};
    // Add categories query
    this.query = Object.assign(this.query,this.categories);
    // Add price query
    this.query = Object.assign(this.query,this.price);

    console.log(this.query);
    let url = this.router.url.split(';')[0];
    this.router.navigate([url, this.query]);
  }
}
