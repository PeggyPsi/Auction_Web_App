import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserHomeComponent } from '../_components/user/user-home.component';
import { UserRoutingModule } from '../_routings/user-routing.module';
import { SharedModule } from './shared.module';
import { ItemModule } from './item.module';
import { SellerItemDetailsComponent } from '../_components/seller/seller-item-details/seller-item-details.component';
import { SellerItemListComponent } from '../_components/seller/seller-item-list/seller-item-list.component';
import { SellerItemEditComponent } from '../_components/seller/seller-item-edit/seller-item-edit.component';
import { TagInputModule } from 'ngx-chips';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { BidderChoicesComponent } from '../_components/bidder/bidder-choices/bidder-choices.component';
import { SellerChoicesComponent } from '../_components/seller/seller-choices/seller-choices.component';
import { SellerHomeComponent } from '../_components/seller/seller-home/seller-home.component';
import { BidderHomeComponent } from '../_components/bidder/bidder-home/bidder-home.component';
import { BidderItemListComponent } from '../_components/bidder/bidder-item-list/bidder-item-list.component';
import { ChatComponent } from '../_components/chat/chat.component';
import { ChatInboxComponent } from '../_components/chat/chat-inbox/chat-inbox.component';
import { ChatOutboxComponent } from '../_components/chat/chat-outbox/chat-outbox.component';
import { ComposeDialogComponent } from '../_components/chat/compose-dialog/compose-dialog.component';
import { DeleteDialogComponent } from '../_components/chat/delete-dialog/delete-dialog.component';
import { UserRatingDialogComponent } from '../_components/user/user-rating-dialog/user-rating-dialog.component';
import { VerificationDialogComponent } from '../_components/register/verification-dialog/verification-dialog.component';

@NgModule({
  declarations: [
    // My user components
    UserHomeComponent,
    SellerItemDetailsComponent,
    SellerItemListComponent,
    SellerItemEditComponent,
    BidderChoicesComponent,
    SellerChoicesComponent,
    SellerHomeComponent,
    BidderHomeComponent,
    BidderItemListComponent,
    ChatComponent,
    ChatInboxComponent,
    ChatOutboxComponent,
    ComposeDialogComponent
  ],
  imports: [
    ItemModule,
    SharedModule,
    TagInputModule, 
    BrowserAnimationsModule,
    CommonModule,
    SharedModule,
    RouterModule,
    BrowserModule,
    ReactiveFormsModule,
    CommonModule,
    UserRoutingModule, 
  ],
  entryComponents: [
    VerificationDialogComponent,
    ComposeDialogComponent,
    DeleteDialogComponent,
    UserRatingDialogComponent
  ]
})

export class UserModule { }
