import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { first } from 'rxjs/operators';
import { ItemsFunctions } from 'src/app/_functions/items-functions';
import { ItemService, AlertService } from 'src/app/_services';

@Component({
  selector: 'app-items-management',
  templateUrl: './items-management.component.html',
  styleUrls: ['./items-management.component.css']
})
export class ItemsManagementComponent implements OnInit {
  items: Item[];
  loadedItems = false;

  constructor(
    private itemService: ItemService,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.items = [];
    this.loadItems();
  }

  loadItems(){
    this.itemService.getAllItems({})
    .pipe(first())
    .subscribe(
      data => {
        this.items = data;
        this.loadedItems = true;
      },
      error => {
        this.alertService.error(error);
        this.loadedItems = true;
      });
  }

  extractToJson(itemId: number) {
    this.itemService.extractToJson(itemId)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.alertService.success(data.message);
      },
      error => {
        this.alertService.clearMessage();
        this.alertService.error(error);
      });
  }

  extractToXml(itemId: number) {
    this.itemService.extractToXml(itemId)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.alertService.success(data.message);
      },
      error => {
        this.alertService.clearMessage();
        this.alertService.error(error);
      });   
  }

}
