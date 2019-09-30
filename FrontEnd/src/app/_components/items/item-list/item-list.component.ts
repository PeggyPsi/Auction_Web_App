import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ItemService, AlertService, AuthenticationService } from 'src/app/_services';
import { first } from 'rxjs/internal/operators/first';
import { Item } from 'src/app/_models/item/item';
import { User } from 'src/app/_models/user';
import { ItemsFunctions } from 'src/app/_functions/items-functions';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {

  @Input() newest: boolean;

  items: Item[];
  loadedItems: boolean;
  currentUser: User;
  // newest 
  newestParam = false;

  constructor(
    private itemService: ItemService,
    private alertService: AlertService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.items = [];
    this.loadItems();
  }

  loadItems(){
    if (this.newest === true) this.loadItemsNewest();
    else this.loadItemsRegular();
  }

  // load newlly added and active auctions (the ones that started)
  loadItemsNewest() {
    this.itemService.getItemsByNewest()
    .pipe(first())
    .subscribe(
      data => {
        this.items = ItemsFunctions.retrieveActive(data);
        if(this.items.length === 0) this.alertService.error("No newly added active auctions could be found...")
        this.loadedItems = true;
      },
      error => {
        this.alertService.error(error);
        this.loadedItems = true;
      });
  }

  // load all items
  loadItemsRegular() {
    this.itemService.getAllItems({})
    .pipe(first())
    .subscribe(
      data => {
        this.loadedItems = true;
      },
      error => {
        this.alertService.error(error);
        this.loadedItems = true;
      });
  }

  onSelect(itemId: number) {
    // If selected item is current user's item -> send him to seller/items
    if(this.currentUser){
      let curItem = this.items.find(item => {
        return item.seller.publicId === this.currentUser.publicId;
      })  
      console.log(curItem);
      if(curItem){
        this.router.navigate(['user/seller/items/id', itemId]);
      }
      else {
        this.router.navigate(['items', itemId]);
      }
    }
    else {
      this.router.navigate(['items', itemId]);
    }
  }

  bidItem(itemId: number) {
    // BID ITEM
    // if not logged in -> redirect to login page
    if(!this.currentUser) this.router.navigate(["/login", {bid: 'true'}]);
    else this.router.navigate(["user/items", itemId]);
  }

}
