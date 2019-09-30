import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';

@Component({
  selector: 'app-forbidden',
  templateUrl: './forbidden.component.html',
  styleUrls: ['./forbidden.component.css']
})
export class ForbiddenComponent implements OnInit {

  currentHome: String;

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit() {
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
  }

}
