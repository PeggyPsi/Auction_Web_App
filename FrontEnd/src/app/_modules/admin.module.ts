import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListUsersComponent } from '../_components/admin/list-users/list-users.component';
import { AdminHomeComponent } from '../_components/admin/admin-home.component';
import { AdminRoutingModule } from '../_routings/admin-routing.module';
import { SharedModule } from './shared.module';
import { AdminUserInfoComponent } from '../_components/admin/admin-user-info/admin-user-info.component';
import { UsersManagementComponent } from '../_components/admin/users-management/users-management.component';
import { ItemsManagementComponent } from '../_components/admin/items-management/items-management.component';

@NgModule({
  declarations: [
    // My admin components
    ListUsersComponent,
    AdminUserInfoComponent,    
    AdminHomeComponent,
    UsersManagementComponent,
    ItemsManagementComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    AdminRoutingModule
  ]
})

export class AdminModule { }
