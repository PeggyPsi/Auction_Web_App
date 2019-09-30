import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { Subscription } from 'rxjs';
import { AuthenticationService, UserService } from 'src/app/_services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  constructor(
    private router: Router
  ) {}

  ngOnInit() {
  }

  manageUsers() {
    this.router.navigate(['/admin/users']);
  }

  manageItems() {
    this.router.navigate(['/admin/items']);
  }

}
