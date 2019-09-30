import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bidder-choices',
  templateUrl: './bidder-choices.component.html',
  styleUrls: ['./bidder-choices.component.css']
})
export class BidderChoicesComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
  }

  biddedAuctions() {
    // navigate to user bidder home -> show bidded items
    this.router.navigate(['/user/bidder/items', 'bidded']);
  }

  wonAuctions() {
    // navigate to user bidder home -> show won items
    this.router.navigate(['/user/bidder/items', 'won']);
  }

}
