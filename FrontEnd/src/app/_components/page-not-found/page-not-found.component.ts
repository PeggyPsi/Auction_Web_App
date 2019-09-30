import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent implements OnInit {

  currentHome: String;

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit() {
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
  }

}
