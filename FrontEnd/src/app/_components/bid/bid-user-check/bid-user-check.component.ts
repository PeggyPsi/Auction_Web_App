import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-bid-user-check',
  templateUrl: './bid-user-check.component.html',
  styleUrls: ['./bid-user-check.component.css']
})
export class BidUserCheckComponent{

  constructor(
    public dialogRef: MatDialogRef<BidUserCheckComponent>,
    @Inject(MAT_DIALOG_DATA) public message: string) { }
    
  onNoClick(): void {
    this.dialogRef.close();
  }

}
