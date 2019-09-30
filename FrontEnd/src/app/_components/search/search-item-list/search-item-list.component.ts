import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { ItemService, AlertService, AuthenticationService } from 'src/app/_services';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { User } from 'src/app/_models/user';
import { ItemsFunctions } from 'src/app/_functions/items-functions';

@Component({
  selector: 'app-search-item-list',
  templateUrl: './search-item-list.component.html',
  styleUrls: ['./search-item-list.component.css']
})
export class SearchItemListComponent implements OnInit {

  items: Item[];
  loadedItems: boolean;
  currentUser: User;

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
      // Sorted by category
      if(params['search'] !== undefined ) {
        this.searchBasedOnText(params['search']);
      }
      else{
        // this.router.navigate(["/browse", "all"]);
        this.loadAll();
      }
    });
  }

  ngOnInit() {
    this.items = [];
  }

  searchBasedOnText(text: string) {
    // Search based on text
    this.itemService.searchByText(text)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.items = data;
        this.items = ItemsFunctions.retrieveActiveAndEnded(this.items);
        this.loadedItems = true;
      },
      error => {
        this.alertService.error(error);
        this.items = [];
        this.loadedItems = true;
    });
  }

  loadAll() {
    this.itemService.getAllItems("")
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.items = data;
        this.items = ItemsFunctions.retrieveActiveAndEnded(this.items);
        this.loadedItems = true;
      },
      error => {
        this.alertService.error(error);
        this.items = [];
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
        this.router.navigate(['/user/items', itemId]);
      }
    }
    else {
      this.router.navigate(['items', itemId]);
    }
  }

}
