import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './_components/home';
import { LoginComponent } from './_components/login';
import { RegisterComponent } from './_components/register';
import { PageNotFoundComponent } from './_components/page-not-found/page-not-found.component';
import { ForbiddenComponent } from './_components/forbidden/forbidden.component';
import { RefreshPageComponent } from './_components/refresh-page/refresh-page.component';
import { ItemListComponent } from './_components/items/item-list/item-list.component';
import { ItemDetailsComponent } from './_components/items/item-details/item-details.component';
import { BrowseComponent } from './_components/browse/browse.component';
import { ErrorServerConnectionComponent } from './_components/error-server-connection/error-server-connection.component';
import { SearchItemListComponent } from './_components/search/search-item-list/search-item-list.component';


const appRoutes: Routes = [
    // VISITORS
    { path: '', component: HomeComponent ,
      children: [
        { path: '', redirectTo: 'items', pathMatch: 'full' },
        { path: 'items', component: ItemListComponent }, 
      ]   
    },
    { path: 'items/:itemId', component: ItemDetailsComponent},
    { path: 'login', component: LoginComponent},
    { path: 'register', component: RegisterComponent },
    { path: 'notfound', component: PageNotFoundComponent }, 
    { path: 'forbidden', component: ForbiddenComponent},
    { path: 'refresh', component: RefreshPageComponent },
    { path: 'browse/:type', component: BrowseComponent },
    { path: 'server_error', component: ErrorServerConnectionComponent },
    { path: 'search', component: SearchItemListComponent},
    { path: '**', redirectTo: 'notfound' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})

export class AppRoutingModule {}
