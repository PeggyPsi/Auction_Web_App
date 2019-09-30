import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { User } from 'src/app/_models/user';
import { ItemService, AlertService, AuthenticationService } from 'src/app/_services';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/_models/category/category';
import { first } from 'rxjs/operators';
import { ItemsFunctions } from 'src/app/_functions/items-functions';

@Component({
  selector: 'app-bidder-item-list',
  templateUrl: './bidder-item-list.component.html',
  styleUrls: ['./bidder-item-list.component.css']
})
export class BidderItemListComponent implements OnInit {

  items: Item[];
  loadedItems: boolean;
  currentUser: User;
  // sorted by
  sortedId: any;
  sortedName: any;
  sortedCategory: string = "";
  // type: active or completed
  type;
  params=null;

  constructor(
    private itemService: ItemService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
  ) { 
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    // get order by params
    this.route.params
    .subscribe(params => {
      // // Sorted by category
      // if(params['category'] !== undefined) {
      //   this.sortedId = params['category'];
      //   this.sortedCategory = "category";
      //   this.sortedName = params['name'];
      // }
      // // Sorted by seller
      // else if(params['seller'] != undefined) {
      //   this.sortedId = params['seller'];
      //   this.sortedCategory = "seller";
      //   this.sortedName = params['name'];
      // }
      // // Sorted by country
      // else if(params['country'] != undefined) {
      //   this.sortedId = params['country'];
      //   this.sortedCategory = "country";
      // }
      // else {
      //   this.sortedCategory = "";
      // }

      // get all, active or completed params
      if(params['type'] != undefined) {
        this.type = params['type'];
      }

      this.params = params;

      this.loadItems();
    });
  }

  ngOnInit() {
    this.items = [];
    // this.loadItems();
  }

  onSelect(item: Item) {
    // If selected item is current user's item -> send him to seller/items
    if(this.currentUser){
      if(item.seller.publicId === this.currentUser.publicId){
        console.log("HEY")
        this.router.navigate(['user/seller/items/id', item.id]);
      }
      else {
        this.router.navigate(['/user/items', item.id]);
      }
    }
    else {
      this.router.navigate(['items', item.id]);
    }
  }

  onCategorySelect(category: Category) {
    let url = this.router.url.split(';')[0];
    this.router.navigate([url, {category: category.id, name: category.name}]);
  }

  loadItems(){
    // check if results must be sorted
    // let query = [];
    // if(this.sortedCategory !== "") {
    //   if(this.sortedCategory === "category") query = ["categoryId", this.sortedId];
    //   else if(this.sortedCategory === "seller") query = ["sellerId", this.sortedId];
    //   else if(this.sortedCategory === "country") query = ["country", this.sortedId];
    // }
    // console.log(query);
    if(this.type === "bidded") this.loadBiddedItems();
    else if(this.type === "won") this.loadWonItems();
  }

  loadBiddedItems(){
    this.itemService.getBiddedItems(this.currentUser.publicId, this.params)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.items = data;
        if(this.items.length === 0)  this.alertService.error("No " + this.type + " auctions could be found.");
        this.loadedItems = true;
      },
      error => {
        this.alertService.error(error);
        this.items = [];
        this.loadedItems = true;
    });
  }

  loadWonItems(){
    this.itemService.getWonItems(this.currentUser.publicId, this.params)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.items = data;
        if(this.items.length === 0)  this.alertService.error("You haven't won any auctions.");
        this.loadedItems = true;
      },
      error => {
        this.alertService.error(error);
        this.items = [];
        this.loadedItems = true;
    });
  }

  // loadItemsByCategory() {
  //   console.log("Sorted by category " + this.sortedId);
  //   this.itemService.getItemsByCategoryAndSellerId(this.sortedId, this.currentUser.publicId)
  //   .pipe(first())
  //   .subscribe(
  //     data => {
  //       this.alertService.clearMessage();
  //       this.items = data;
  //       if(this.type === "active") this.items = ItemsFunctions.retrieveActive(this.items);
  //       else if(this.type === "completed") this.items = ItemsFunctions.retrieveCompleted(this.items);
  //       else if(this.type === "inactive") this.items = ItemsFunctions.retrieveInactive(this.items);
  //       if(this.items.length === 0)  this.alertService.error("No " + this.type + " auctions could be found for specific category.");
  //       this.loadedItems = true;
  //     },
  //     error => {
  //       this.alertService.error(error);
  //       this.items = [];
  //       this.loadedItems = true;
  //   });
  // }

  // loadItemsBySeller() {
  //   console.log("Sorted by seller " + this.sortedId);
  // }

  // loadItemsByCountry() {
  //   console.log("Sorted by country " + this.sortedId);
  // }

}
