import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-items-type',
  templateUrl: './items-type.component.html',
  styleUrls: ['./items-type.component.css']
})
export class ItemsTypeComponent implements OnInit {
  type;
  url;
  query;

  constructor(
    private router: Router
  ) { 
    this.type = "all";
    this.url = this.router.url.split(';',1)[0];
    // // browsing
    // if(this.url.includes('browse')) this.url = '/browse';
    if(this.url.includes('bidder')) {
      this.type = this.url.split('/')[4];
    }
  }

  ngOnInit() {
  }

  onClick(type: string) {
    this.type = type;
    let fields = this.router.url.split(';');
    console.log(fields);
    this.url = fields[0];
    fields.splice(0,1);
    this.query = fields[1] != undefined ? ';'+ fields.join(';') : "";
    console.log("current query in items-type: " + this.query);

    // browsing
    if(this.url.includes('browse')) this.url = '/browse';
    else if(this.url.includes('seller')) this.url = '/user/seller/items';
    else if(this.url.includes('bidder')) this.url = '/user/bidder/items';

    // Retrieve active auctions
    if(type === "active") this.router.navigateByUrl(this.url + "/active" + this.query);
    // Retrieve completed auctions
    else if(type === "completed") this.router.navigateByUrl(this.url + "/completed" + this.query);
    // Retrieve all auctions
    else if(type === "all" && this.url.includes('seller') || this.url.includes('browse')) this.router.navigateByUrl(this.url + "/all" + this.query);

    // Only for seller
    if(this.url.includes('seller')) {
      if(type === "inactive") this.router.navigateByUrl(this.url + "/inactive" + this.query);
    }

    // Only for bidder 
    if(this.url.includes('bidder')) {
      if(type === "bidded") this.router.navigateByUrl(this.url + "/bidded" + this.query);
      else if(type === "won") this.router.navigateByUrl(this.url + "/won" + this.query);
    }    
  }

}
