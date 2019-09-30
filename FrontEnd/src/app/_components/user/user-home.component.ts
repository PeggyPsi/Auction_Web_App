import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {

  currentUser: User;

  constructor(
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService) { 
  }

  ngOnInit() {
    // let publicId = this.route.snapshot.paramMap.get('publicId');
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

}
