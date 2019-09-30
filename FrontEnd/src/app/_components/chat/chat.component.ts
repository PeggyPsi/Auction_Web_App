import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { BidUserCheckComponent } from '../bid/bid-user-check/bid-user-check.component';
import { ComposeDialogComponent } from './compose-dialog/compose-dialog.component';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(
    // private dialog: MatDialog
  ) { }

  ngOnInit() {
  }

  // composeDialog() {
  //   const dialogRef = this.dialog.open(ComposeDialogComponent, {
  //     width: '600px',
  //     data: ""
  //   });

  //   dialogRef.afterClosed().subscribe(result => {
  //     // this.saveMessage(result);
  //     console.log(result);
  //   });
  // }
  // saveMessage()

}
