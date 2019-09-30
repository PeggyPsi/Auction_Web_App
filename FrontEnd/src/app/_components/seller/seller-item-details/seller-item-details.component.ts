import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemService, AlertService, AppService, AuthenticationService, MessageService, RatingService } from 'src/app/_services';
import { first } from 'rxjs/internal/operators/first';
import { Item } from 'src/app/_models/item/item';
import { DateCompare } from 'src/app/_functions/date-compare';
import { MatDialog } from '@angular/material';
import { StartAuctionDialogComponent } from './start-auction-dialog/start-auction-dialog.component';
import { User } from 'src/app/_models/user';
import { ComposeDialogComponent } from '../../chat/compose-dialog/compose-dialog.component';
import { Message } from 'src/app/_models/message/message';
import { UserRatingDialogComponent } from '../../user/user-rating-dialog/user-rating-dialog.component';
import { DeleteDialogComponent } from '../../chat/delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-seller-item-details',
  templateUrl: './seller-item-details.component.html',
  styleUrls: ['./seller-item-details.component.css']
})
export class SellerItemDetailsComponent implements OnInit {

  item: Item;
  disableEditingDeletion: boolean;
  loadedItem: boolean;
  message: string;
  currentUser: User;
  ratingForBuyerPlaced: string;

  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private alertService: AlertService,
    private router: Router,
    private dialog: MatDialog,
    private appService: AppService,
    private authenticationService: AuthenticationService,
    private messageService: MessageService,
    private ratingService: RatingService
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.item = new Item();
    this.ratingForBuyerPlaced = "false";
    // Retrieve itemId parameter from request url
    let itemId = this.route.snapshot.paramMap.get('itemId');
    // Load specific item
    this.loadItemDetails(+itemId);          // + converts string to number  
  }

  onSelect(itemId: number) {
    this.router.navigate(['user/seller/items/id', itemId]);
  }

  disableButtons() {
    // Check if start date has not come yet
    // Get current date
    let message = "";
    // let currentDate = new Date().toISOString().substr(0, 16);
    // let auctionStart = this.item.auctionStart.toString().substr(0, 16);
    // Check if approved time to delete the item
    if (this.item.ended) {
      message = "Auction has been ended!";
      this.disableEditingDeletion = true;
    }
    else if (this.item.active) {
      // this.alertService.error("Auction has already started!");
      // console.log(this.item.auctionStart);
      message = "Auction has already started!";
      this.disableEditingDeletion = true;
    }
    // Check if current bid exists (at least one has bidded). If it does you can not delete teh specific item.
    else if(this.item.noBids !== 0) {
      // console.log("HEY");
      // this.alertService.success("Someone has already put his bid on the item!");
      message = "Someone has already put his bid on the item!";
      this.disableEditingDeletion = true;
    }
    if(this.disableEditingDeletion === true) {
      this.alertService.error("You cannot delete or edit the specific item. "+message);
    }
  }

  checkIfBuyerRated() {
    this.ratingService.existsRatingForBy(this.item.seller.publicId, this.currentUser.publicId)
    .pipe(first())
    .subscribe(
      data => {
        console.log(data);
        this.ratingForBuyerPlaced = data.message;
        console.log(this.ratingForBuyerPlaced);
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
        this.loadedItem = true;
        this.item = data;
        console.log(this.item);
        // Check whether to disable edit and deletion buttons
        this.disableEditingDeletion = false;
        this.disableButtons();
        // this.alertService.success(this.message);

        // Check if user has already rated buyer (only item is sold)
        if(this.item.buyer != null) {
          this.checkIfBuyerRated();
        }
      },
      error=> {
        // console.error(error.error.message);
        this.alertService.error(error);
      }
    );
  }

  deleteItem() {
    console.log("HELLO");
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '600px',
      data: "Are you sure you want to delete this item?"
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        console.log("He wants to delete");
        this.itemService.deleteItem(this.item.id)
        .pipe(first())
        .subscribe(
          data => {
            this.alertService.success(data.message);
            // this.disableEditingDeletion = true;
            this.router.navigate(['/user/seller/items', 'all']);
          },
          error=> {
            this.alertService.error("There was an error when deleting the item");
          }
        );
      }
    }); 
  }

  editItem() {
    this.router.navigate(["user/seller/items/id", this.item.id, "edit"]);
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(StartAuctionDialogComponent, {
      width: '300px',
      data: ""
    });

    dialogRef.afterClosed().subscribe(result => {
      // Update Auction -> Active
      if(result === true) {
        this.startAuction();
      }
    });
  }

  startAuction() {
    console.log("start auction");
    // Update item start auction with current date
    this.itemService.startAuction(this.item.id).
    pipe(first())
    .subscribe(
      data => {
        // Auction started successfully -> refresh page
        this.appService.refreshPage(["user/seller/items/id", this.item.id]);
      },
      error=> {
        this.alertService.error(error);
      }
    );

    this.appService.refreshPage(["user/seller/items/id", this.item.id]);
  }

  messageBuyer(buyer: User) {
    const dialogRef = this.dialog.open(ComposeDialogComponent, {
      width: '600px',
      data: {to: buyer.username, message: ""}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.saveMessage(buyer, this.currentUser, result);
      // console.log(result);
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
    messageModel.fromRole = "seller";
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

  rateBuyer() {

    const dialogRef = this.dialog.open(UserRatingDialogComponent, {
      width: '600px',
      // height: '300px',
      data: "Your rating for " + this.item.buyer.username
    });

    dialogRef.afterClosed().subscribe(result => {
      // Save rating to database
      if(result !== null) {
        this.ratingService.rateBuyer(this.currentUser.publicId, this.item.buyer.publicId, result)
        .pipe(first())
        .subscribe(
          data => {
            // this.alertService.success(data.message);
            this.ratingForBuyerPlaced = "true";
          },
          error=> {
            // this.alertService.error(error);
          }
        );
      }
    });
  }

}
