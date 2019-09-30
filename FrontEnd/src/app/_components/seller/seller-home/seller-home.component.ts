import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { AuthenticationService } from 'src/app/_services';

@Component({
  selector: 'app-seller-home',
  templateUrl: './seller-home.component.html',
  styleUrls: ['./seller-home.component.css']
})
export class SellerHomeComponent implements OnInit {

  constructor(
  ) { 
  }

  ngOnInit() {
  }

}
