import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserHomeComponent } from '../_components/user/user-home.component';
import { AuthGuard } from '../_guards';
import { ItemListComponent } from '../_components/items/item-list/item-list.component';
import { ItemCreateComponent } from '../_components/items/item-create/item-create.component';
import { SellerItemDetailsComponent } from '../_components/seller/seller-item-details/seller-item-details.component';
import { SellerItemListComponent } from '../_components/seller/seller-item-list/seller-item-list.component';
import { ItemDetailsComponent } from '../_components/items/item-details/item-details.component';
import { SellerItemEditComponent } from '../_components/seller/seller-item-edit/seller-item-edit.component';
import { BrowseComponent } from '../_components/browse/browse.component';
import { SellerHomeComponent } from '../_components/seller/seller-home/seller-home.component';
import { BidderHomeComponent } from '../_components/bidder/bidder-home/bidder-home.component';
import { ChatComponent } from '../_components/chat/chat.component';
import { ChatInboxComponent } from '../_components/chat/chat-inbox/chat-inbox.component';
import { ChatOutboxComponent } from '../_components/chat/chat-outbox/chat-outbox.component';

const userRoutes:  Routes  = [
  { path: 'user', component: UserHomeComponent, canActivate: [AuthGuard], data : { role : 'SITE_USER'},
    children: [
      { path: '', redirectTo: 'items', pathMatch: 'full'},
      { path: 'items', component: ItemListComponent }
    ]
  },

  { path: 'user/seller', component: SellerHomeComponent, canActivate: [AuthGuard], data : { role : 'SITE_USER'},
    // children: [
    //   { path: '', redirectTo: 'items', pathMatch: 'full'},
    //   { path: 'items', component: SellerItemListComponent }
    // ]
  },

  { path: 'user/chat', component: ChatComponent , canActivate: [AuthGuard], data : { role : 'SITE_USER'},
    children: [
      { path: '', redirectTo: 'inbox', pathMatch: 'full'},
      { path: 'inbox', component: ChatInboxComponent },
      { path: 'outbox', component: ChatOutboxComponent },
    ]    
  },
  // { path: 'user/chat', redirectTo: 'user/chat/inbox'},

  { path: 'user/seller/items/:type', component: SellerHomeComponent , canActivate: [AuthGuard], data : { role : 'SITE_USER'}},
  { path: 'user/bidder/items/:type', component: BidderHomeComponent , canActivate: [AuthGuard], data : { role : 'SITE_USER'}},

  // TODO THINK ABOUT AUTHGUARD beTweN USeRS
  { path: 'user/seller/sellitem', component: ItemCreateComponent, canActivate: [AuthGuard], data : { role : 'SITE_USER'} },
  { path: 'user/seller/items/id/:itemId', component: SellerItemDetailsComponent, canActivate: [AuthGuard], data : { role : 'SITE_USER'} },
  { path: 'user/seller/items/id/:itemId/edit', component: SellerItemEditComponent, canActivate: [AuthGuard], data : { role : 'SITE_USER'} },

  { path: 'user/items/:itemId', component: ItemDetailsComponent, canActivate: [AuthGuard], data : { role : 'SITE_USER'} },

];

@NgModule({
  imports: [
    RouterModule.forChild(userRoutes)
  ],
  exports: [
    RouterModule
  ]
})

export class UserRoutingModule { }
