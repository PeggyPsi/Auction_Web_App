import { Component, OnInit, Input } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { MatDialogModule, MatDialog } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BidService, AuthenticationService, AlertService, AppService } from 'src/app/_services';
import { Bid } from 'src/app/_models/bid/bid';
import { User } from 'src/app/_models/user';
import { first } from 'rxjs/internal/operators/first';
import { BidCheckValidator } from 'src/app/_custom-validators/bid/bid-check.validator';
import { BidUserCheckComponent } from '../bid-user-check/bid-user-check.component';
import { Router } from '@angular/router';


@Component({
  selector: 'app-bid-form',
  templateUrl: './bid-form.component.html',
  styleUrls: ['./bid-form.component.css']
})
export class BidFormComponent implements OnInit {
  @Input() item: Item;

  bidForm: FormGroup;
  submitted = false;
  loading = false;
  bidUserCheck;
  currentUser: User;

  constructor(
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private bidService: BidService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private router: Router,
    private appService: AppService
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.bidForm = this.formBuilder.group({
      amount: ['', Validators.compose([
          // 1. Bid Field is Required
          Validators.required,
          // 2. Bid must be bigger than current bid
          BidCheckValidator.bidCheckValidator(this.item.currentBid)
        ])
      ]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.bidForm.controls; }

  bid() {
    // check if user not logged in
    if(!this.currentUser) {
      this.router.navigate(["/login", {bid: true, id: this.item.id}]);
    }
    else {
      this.submitted = true;

      // stop here if form is invalid
      if (this.bidForm.invalid) {
        return;
      }

      console.log("BIDDING");
  
      // ask user if he is sure about the specific bid
      if(this.item.buyOffer != null && this.f.amount.value >= this.item.buyOffer) {
        this.openDialog("You are about to buy this item for " + this.f.amount.value + "$. Are you sure?", true, this.f.amount.value );
      }
      else {
        this.openDialog("Are you sure you want to bid on this item?", false, null);
      }
      
    }
  }

  buy() {
    // check if user not logged in
    if(!this.currentUser) {
      this.router.navigate(["/login", {bid: true, id: this.item.id}]);
    }
    else {
      // ask user if he is sure about the specific bid
      if(this.item.buyOffer != null && this.f.amount.value >= this.item.buyOffer) {
        this.openDialog("You are about to buy this item for " + this.f.amount.value + "$. Are you sure?", true, this.f.amount.value );
      }
      else {
        this.openDialog("Are you sure you want to buy this item for " + this.item.buyOffer + "$ ?", true, this.item.buyOffer);
      }
    }
  }

  openDialog(message: string, buy: boolean, price: number): void {
    const dialogRef = this.dialog.open(BidUserCheckComponent, {
      width: '400px',
      data: message
    });

    dialogRef.afterClosed().subscribe(result => {
      this.bidUserCheck = result;
      if(buy) {
        this.submitBuying(price);
      }
      else{
        this.submitBid();
      }
    });
  }

  submitBid() {
    // if user is not sure
    if(this.bidUserCheck !== true) return;
    // else user is sure -> continue with saving bid
    else if(this.bidUserCheck === true) {
      // this.loading = true;

      // this.router.navigate(['/items', this.item.id]);
      
      // complete values of bid model
      let bid = new Bid();
      bid.item = this.item;
      bid.bidder = this.currentUser;
      bid.amount = this.f.amount.value;
      bid.time = new Date();
      console.log(bid.time);
      console.log(bid);
      this.bidService.addBid(bid, false)
        .pipe(first())
        .subscribe(
          data => {
            // this.alertService.success(data.message);
            // this.loading = false;
            // this.router.navigate(['/items', this.item.id]);
            this.appService.refreshPage(['/items', this.item.id]);
          },
          error => {
            this.alertService.error(error);
            // this.loading = false;
          });
    }
  }

  submitBuying(price: number) {
    // if user is not sure
    if(this.bidUserCheck !== true) return;
    // else user is sure -> continue with saving bid
    else if(this.bidUserCheck === true) {
      // this.loading = true;

      // this.router.navigate(['/items', this.item.id]);
      
      // complete values of bid model
      let bid = new Bid();
      bid.item = this.item;
      bid.bidder = this.currentUser;
      bid.amount = price;
      bid.time = new Date();
      console.log(bid.time);
      console.log(bid);
      this.bidService.addBid(bid, true)
        .pipe(first())
        .subscribe(
          data => {
            // this.alertService.success(data.message);
            // this.loading = false;
            // this.router.navigate(['/items', this.item.id]);
            this.appService.refreshPage(['/items', this.item.id]);
          },
          error => {
            this.alertService.error(error);
            // this.loading = false;
          });
    }
  }

}
