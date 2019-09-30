import { NgModule } from  '@angular/core';
import { Routes, RouterModule } from  '@angular/router';
import { AuthGuard } from '../_guards';
import { AdminHomeComponent } from '../_components/admin/admin-home.component';
import { ListUsersComponent } from '../_components/admin/list-users/list-users.component';
import { AdminUserInfoComponent } from '../_components/admin/admin-user-info/admin-user-info.component';
import { UsersManagementComponent } from '../_components/admin/users-management/users-management.component';
import { ItemsManagementComponent } from '../_components/admin/items-management/items-management.component';

const  adminRoutes:  Routes  = [
  { path: 'admin', component: AdminHomeComponent, canActivate: [AuthGuard], data : { role : 'ADMIN' }},
  { path: 'admin/users', component: UsersManagementComponent, canActivate: [AuthGuard], data : { role : 'ADMIN' }},
  { path: 'admin/items', component: ItemsManagementComponent, canActivate: [AuthGuard], data : { role : 'ADMIN' }},
  { path: 'admin/users/info/:publicId', component: AdminUserInfoComponent, canActivate: [AuthGuard], data : { role : 'ADMIN'}}
];

@NgModule({
  imports: [
    RouterModule.forChild(adminRoutes)
  ],
  exports: [
    RouterModule
  ]
})

export  class  AdminRoutingModule { }