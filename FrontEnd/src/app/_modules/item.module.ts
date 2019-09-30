import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemListComponent } from '../_components/items/item-list/item-list.component';
import { SharedModule } from './shared.module';
import { ItemCreateComponent } from '../_components/items/item-create/item-create.component';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { TagInputModule } from 'ngx-chips';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ItemDetailsComponent } from '../_components/items/item-details/item-details.component';
import { BidFormComponent } from '../_components/bid/bid-form/bid-form.component';
import { HighestBidsComponent } from '../_components/bid/highest-bids/highest-bids.component';
import { MatDialogModule, MatButtonModule } from '@angular/material';
import { BidUserCheckComponent } from '../_components/bid/bid-user-check/bid-user-check.component';
import { StartAuctionDialogComponent } from '../_components/seller/seller-item-details/start-auction-dialog/start-auction-dialog.component';

@NgModule({
  declarations: [
    ItemListComponent,
    ItemCreateComponent,
    ItemDetailsComponent,
    BidUserCheckComponent,
    BidFormComponent,
    HighestBidsComponent,
    StartAuctionDialogComponent
  ],
  imports: [
    MatDialogModule,
    MatButtonModule,
    TagInputModule, 
    BrowserAnimationsModule,
    CommonModule,
    SharedModule,
    RouterModule,
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  exports: [
    ItemListComponent,
    ItemCreateComponent,
    ItemDetailsComponent,
    BidUserCheckComponent,
    BidFormComponent,
    HighestBidsComponent,
    StartAuctionDialogComponent
  ],
  entryComponents: [
    BidUserCheckComponent,
    StartAuctionDialogComponent
  ]
})

export class ItemModule { }
