import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { User } from 'src/app/_models/user';
import { AuthenticationService, ItemService, AlertService } from 'src/app/_services';
import { first } from 'rxjs/internal/operators/first';
import { Router, ActivatedRoute } from '@angular/router';
import { ItemsFunctions } from 'src/app/_functions/items-functions';
import { Category } from 'src/app/_models/category/category';

@Component({
  selector: 'app-seller-item-list',
  templateUrl: './seller-item-list.component.html',
  styleUrls: ['./seller-item-list.component.css']
})
export class SellerItemListComponent implements OnInit {

  items: Item[];
  loadedItems: boolean;
  currentUser: User;
  // sorted by
  sortedId: any;
  sortedName: any;
  sortedCategory: string;
  // type: active or completed
  type;
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
    this.route.params
    .subscribe(params => {
      // console.log(params);
      // console.log("HEY");
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

      this.loadItemsRegular();
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

  // loadItems(){
  //   if(this.sortedCategory === "category") this.loadItemsByCategory();
  //   else if(this.sortedCategory === "seller") this.loadItemsBySeller();
  //   else if(this.sortedCategory === "country") this.loadItemsByCountry();
  //   else this.loadItemsRegular();
  // }

  loadItemsRegular() {
    this.itemService.getItemsBySellerId(this.currentUser.publicId, this.params)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.items = data;
        if(this.type === "active") this.items = ItemsFunctions.retrieveActive(this.items);
        else if(this.type === "completed") this.items = ItemsFunctions.retrieveCompleted(this.items);
        else if(this.type === "inactive") this.items = ItemsFunctions.retrieveInactive(this.items);
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




// items: Item[];
// currentUser: User;
// loadedItems: boolean;

// constructor(
//   private authenticationService: AuthenticationService,
//   private itemService: ItemService,
//   private alertService: AlertService,
//   private router: Router
// ) {}

// ngOnInit() {
//   this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
//   this.items = [];
//   this.loadItems(this.currentUser.publicId);
// }

// loadItems(sellerId: string) {
//   this.itemService.getItemsBySellerId(this.currentUser.publicId)
//   .pipe(first())
//   .subscribe(
//     data => {
//       this.items = data;
//       this.loadedItems = true;
//     },
//     error => {
//       this.alertService.error(error);
//       this.loadedItems = true;
//     });
// }

// onSelect(itemId: number) {
//   this.router.navigate(["user/seller/items/id", itemId]);
// }
