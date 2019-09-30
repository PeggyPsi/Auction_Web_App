import { Component, OnInit, Input } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { Bid } from 'src/app/_models/bid/bid';
import { BidService, AlertService } from 'src/app/_services';
import { first } from 'rxjs/internal/operators/first';

@Component({
  selector: 'app-highest-bids',
  templateUrl: './highest-bids.component.html',
  styleUrls: ['./highest-bids.component.css']
})
export class HighestBidsComponent implements OnInit {

  @Input() item: Item;

  bids: Bid[];
  loadedBids = false;
  emptySlots = [];
  maxSlots = 3;

  constructor(
    private bidService: BidService,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.bids = [];
    this.loadBids();
  }

  loadBids() {
    this.bidService.getBids(this.item.id, this.maxSlots)
    .pipe(first())
    .subscribe(
      data => {
        this.bids = data;
        this.emptySlots = Array(this.maxSlots - this.bids.length).fill(0).map((x, index) => this.bids.length + index +1);
        this.loadedBids = true;
      },
      error=> {
        // this.alertService.error(error);
        this.emptySlots = Array(this.maxSlots).fill(0).map((x, index) => index);
        this.loadedBids = true;
      }
    );
  }

}
