import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {

  currentHome: String;

  constructor(private authenticationService: AuthenticationService) {}

  ngOnInit() {
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
  }

}
