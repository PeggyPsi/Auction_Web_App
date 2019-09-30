import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-start-auction-dialog',
  templateUrl: './start-auction-dialog.component.html',
  styleUrls: ['./start-auction-dialog.component.css']
})
export class StartAuctionDialogComponent{

  constructor(
    public dialogRef: MatDialogRef<StartAuctionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }
    
  onNoClick(): void {
    this.dialogRef.close();
  }

}
