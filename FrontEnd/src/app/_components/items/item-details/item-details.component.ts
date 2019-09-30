import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/_models/item/item';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemService, AlertService, AuthenticationService, MessageService, RatingService } from 'src/app/_services';
import { first } from 'rxjs/internal/operators/first';
import { User } from 'src/app/_models/user';
import { ComposeDialogComponent } from '../../chat/compose-dialog/compose-dialog.component';
import { MatDialog } from '@angular/material';
import { Message } from 'src/app/_models/message/message';
import { UserRatingDialogComponent } from '../../user/user-rating-dialog/user-rating-dialog.component';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {

  item: Item;
  loadedItem: boolean;
  currentUser: User;
  ratingForSellerPlaced: string;

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private dialog: MatDialog,
    private router: Router,
    private messageService: MessageService,
    private ratingService: RatingService
  ) { 
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.item = new Item();
    this.ratingForSellerPlaced = "false";
    // Retrieve itemId parameter from request url
    let itemId = this.route.snapshot.paramMap.get('itemId');
    // Load specific item
    this.loadItemDetails(+itemId);          // + converts string to number
  }

  checkIfSellerRated() {
    this.ratingService.existsRatingForBy(this.item.seller.publicId, this.currentUser.publicId)
    .pipe(first())
    .subscribe(
      data => {
        console.log(data);
        this.ratingForSellerPlaced = data.message;
        console.log(this.ratingForSellerPlaced);
      },
      error=> {
        // console.error(error.error.message);
        // this.alertService.error(error);
      }
    );
  }

  loadItemDetails(itemId: number) {
    // Retrieve item from database with its pictures
    this.itemService.getItem(itemId)
    .pipe(first())
    .subscribe(
      data => {
        this.item = data;
        this.loadedItem = true;
        // Check if user has already rated seller (only if user is logged in and has won specific item)
        if(this.currentUser) {
          if(this.currentUser.publicId === this.item.buyer.publicId) {
            this.checkIfSellerRated();
          }
        }
      },
      error=> {
        // console.error(error.error.message);
        this.alertService.error(error);
      }
    );
  }

  messageSeller(seller: User) {
    const dialogRef = this.dialog.open(ComposeDialogComponent, {
      width: '600px',
      data: {to: seller.username, message: ""}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result!=null) this.saveMessage(seller, this.currentUser, result);
    });
  }

  saveMessage(to: User, from: User, message: string) {
    let messageModel = new Message();
    let date  = new Date();
    messageModel.dateTime = new Date(date.toISOString().substr(0,10) + "T" + date.toTimeString().substr(0,5));
    messageModel.fromUser = from;
    messageModel.toUser = to;
    messageModel.id = null;
    messageModel.seen = false;
    messageModel.fromRole = "buyer";
    messageModel.message = message;
    messageModel.deletedFrom = false;
    messageModel.deletedTo = false;
    console.log(messageModel);
    this.messageService.saveMessage(messageModel)
    .pipe(first())
    .subscribe(
      data => {
        this.router.navigate(["/user/chat/inbox"]);
      },
      error=> {
        this.alertService.error(error);
      }
    );
  }

  rateSeller() {

    const dialogRef = this.dialog.open(UserRatingDialogComponent, {
      width: '600px',
      // height: '300px',
      data: "Your rating for " + this.item.seller.username
    });

    dialogRef.afterClosed().subscribe(result => {
      // Save rating to database
      if(result !== null) {
        this.ratingService.rateSeller(this.currentUser.publicId, this.item.seller.publicId, result)
        .pipe(first())
        .subscribe(
          data => {
            // this.alertService.success(data.message);
            this.ratingForSellerPlaced = "true";
          },
          error=> {
            // this.alertService.error(error);
          }
        );
      }
    });
  }
}
