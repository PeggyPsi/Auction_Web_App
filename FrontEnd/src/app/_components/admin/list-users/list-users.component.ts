import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user/user';
import { UserService, AlertService, AuthenticationService } from 'src/app/_services';
import { first, map, catchError } from 'rxjs/operators';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit {

  users: User[];
  currentHome: String;

  constructor(private userService: UserService, 
    private alertService: AlertService, 
    private authenticationService: AuthenticationService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.users = [];
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
    this.loadUsers();
  }

  public loadUsers() {
    this.userService.getAll().pipe(first()).subscribe(users => {
      this.users = users;
    });
  }

  public showUserInfo(public_id: string) {
    this.router.navigate(['userinfo', public_id], {relativeTo: this.route});
  }
}
