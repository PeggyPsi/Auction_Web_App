import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-seller-choices',
  templateUrl: './seller-choices.component.html',
  styleUrls: ['./seller-choices.component.css']
})
export class SellerChoicesComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  // Go to seller auctions
  auctions() {
    this.router.navigate(['/user/seller/items', 'all']);
  }

  sell() {
    this.router.navigate(['user/seller/sellitem']);
  }
}
