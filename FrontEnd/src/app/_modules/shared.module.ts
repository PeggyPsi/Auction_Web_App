// Module that contains or the declarations of components that are used between 
// different components of different modules

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from '../_components/alert';
import { UserInfoComponent } from '../_components/user/user-info/user-info.component';
import { RefreshPageComponent } from '../_components/refresh-page/refresh-page.component';
import { RouterModule } from '@angular/router';
import { PageNotFoundComponent } from '../_components/page-not-found/page-not-found.component';
import { ForbiddenComponent } from '../_components/forbidden/forbidden.component';
import { ImageCarouselComponent } from '../_components/image-carousel/image-carousel.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowseComponent } from '../_components/browse/browse.component';
import { BrowseItemListComponent } from '../_components/browse-item-list/browse-item-list.component';
import { CategoryListComponent } from '../_components/category-list/category-list.component';
import { ItemsTypeComponent } from '../_components/items/items-type/items-type.component';
import { MapItemDetailsComponent } from '../_components/open-street-map/map-item-details/map-item-details.component';
import { MapItemCreateComponent } from '../_components/open-street-map/map-item-create/map-item-create.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DeleteDialogComponent } from '../_components/chat/delete-dialog/delete-dialog.component';
import { MatDialogModule } from '@angular/material';
import { PriceFilterComponent } from '../_components/filters/price-filter/price-filter.component';
import { FiltersComponent } from '../_components/filters/filters.component';
import { UserRatingDialogComponent } from '../_components/user/user-rating-dialog/user-rating-dialog.component';
import { SearchBarComponent } from '../_components/search/search-bar/search-bar.component';
import { ErrorServerConnectionComponent } from '../_components/error-server-connection/error-server-connection.component';
import { SearchItemListComponent } from '../_components/search/search-item-list/search-item-list.component';
import { VerificationDialogComponent } from '../_components/register/verification-dialog/verification-dialog.component';

@NgModule({
  declarations: [
    ImageCarouselComponent,
    AlertComponent,
    UserInfoComponent,
    RefreshPageComponent,
    PageNotFoundComponent,
    ForbiddenComponent ,
    BrowseComponent,
    BrowseItemListComponent,
    CategoryListComponent,
    ItemsTypeComponent,
    MapItemDetailsComponent,
    MapItemCreateComponent,
    DeleteDialogComponent,
    PriceFilterComponent,
    FiltersComponent,
    UserRatingDialogComponent,
    SearchBarComponent,
    SearchItemListComponent,
    ErrorServerConnectionComponent,
    VerificationDialogComponent
  ],
  imports: [
    MatDialogModule,
    ReactiveFormsModule,
    NgbModule,
    CommonModule,
    RouterModule
  ],
  exports: [
    ImageCarouselComponent,
    AlertComponent,
    UserInfoComponent,
    RefreshPageComponent,
    PageNotFoundComponent,
    ForbiddenComponent,
    BrowseComponent,
    BrowseItemListComponent,
    CategoryListComponent,
    ItemsTypeComponent,
    MapItemDetailsComponent,
    MapItemCreateComponent,
    PriceFilterComponent,
    FiltersComponent,
    UserRatingDialogComponent,
    SearchBarComponent,
    SearchItemListComponent,
    ErrorServerConnectionComponent,
    VerificationDialogComponent
  ]
})


export class SharedModule { }
