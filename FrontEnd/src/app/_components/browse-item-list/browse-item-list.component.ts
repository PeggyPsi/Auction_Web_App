import { Component, OnInit, OnDestroy } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { first } from 'rxjs/operators';
import { ItemService } from 'src/app/_services/item.service';
import { AlertService } from 'src/app/_services/alert.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services';
import { ItemsFunctions } from 'src/app/_functions/items-functions';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-browse-item-list',
  templateUrl: './browse-item-list.component.html',
  styleUrls: ['./browse-item-list.component.css']
})
export class BrowseItemListComponent implements OnInit, OnDestroy {

  items: Item[];
  loadedItems: boolean;
  currentUser: User;
  // sorted by
  sortedId: any;
  sortedName: any;
  sortedCategory: string;
  // type: active or completed
  type;
  subscription;
  params = null;

  constructor(
    private itemService: ItemService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
  ) { 
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    // get order by params
    this.subscription = this.route.params
    .subscribe(params => {
      // console.log("--------------------")
      // console.log("browse item list params")
      // console.log(this.router.url);
      // console.log(params);
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
      // else{
      //   this.sortedCategory = "";
      // }

      // // get all, active or completed params
      if(params['type'] != undefined) {
        this.type = params['type'];
      }

      this.params = params;

      this.loadItemsRegular();
    });
  }

  ngOnInit() {
    this.items = [];
    // this.loadItems();
  }

  ngOnDestroy(){
    if(this.subscription) this.subscription.unsubscribe();
  }

  onSelect(item: Item) {
    // If selected item is current user's item -> send him to seller/items
    if(this.currentUser){
      // let curItem = this.items.find(item => {
      //   return item.seller.publicId === this.currentUser.publicId && item.id === itemId;
      // })  
      // console.log(curItem);
      if(item.seller.publicId === this.currentUser.publicId){
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

  // loadItems(){
  //   if(this.sortedCategory === "category") this.loadItemsByCategory();
  //   else if(this.sortedCategory === "country") this.loadItemsByCountry();
  //   else this.loadItemsRegular();
  // }

  loadItemsRegular() {
    this.itemService.getAllItems(this.params)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.items = data;
        if(this.type === "active") this.items = ItemsFunctions.retrieveActive(this.items);
        else if(this.type === "completed") this.items = ItemsFunctions.retrieveCompleted(this.items);
        else if(this.type === "all") this.items = ItemsFunctions.retrieveActiveAndEnded(this.items);
        if(this.items.length === 0)  this.alertService.error("No items found on " + this.type + " auctions .");
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
  //   this.itemService.getItemsByCategory(this.sortedId)
  //   .pipe(first())
  //   .subscribe(
  //     data => {
  //       this.alertService.clearMessage();
  //       this.items = data;
  //       if(this.type === "active") this.items = ItemsFunctions.retrieveActive(this.items);
  //       else if(this.type === "completed") this.items = ItemsFunctions.retrieveCompleted(this.items);
  //       else if(this.type === "all") this.items = ItemsFunctions.retrieveActiveAndEnded(this.items);
  //       if(this.items.length === 0)  this.alertService.error("No " + this.type + " auctions could be found for specific category.");
  //       this.loadedItems = true;
  //     },
  //     error => {
  //       this.alertService.error(error);
  //       this.items = [];
  //       this.loadedItems = true;
  //   });
  // }

  // loadItemsByCountry() {
  //   console.log("Sorted by country " + this.sortedId);
  // }

}
