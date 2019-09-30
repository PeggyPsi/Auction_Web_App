import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  currentUser: User;
  currentHome: String;
  numNewMessages: Number;

  ngOnInit() {
      // console.log("HELLO");
  }

  constructor(
      private router: Router,
      private authenticationService: AuthenticationService
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
    this.authenticationService.numNewMessages.subscribe(x => this.numNewMessages = x);
  }

  logout() {
      this.authenticationService.logout();
      this.router.navigate(['/']);
  }

  search() {
    console.log("I'm searching");           //TODO implement search -> navigate to SearchComponent
  }

  sellItem() {
    // user is logged in
    if(this.currentUser){
      this.router.navigate(['/user/seller/sellitem']);
    }
    else{
      //user must be logged in to sell an item
      //cant register now because he will have to wit for authentication from admin
      this.router.navigate(['/login']).then(() => {
          console.log("/login");
        } 
      );
    }
  }

}
