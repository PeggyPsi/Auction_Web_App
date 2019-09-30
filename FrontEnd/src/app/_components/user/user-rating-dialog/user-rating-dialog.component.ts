import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-user-rating-dialog',
  templateUrl: './user-rating-dialog.component.html',
  styleUrls: ['./user-rating-dialog.component.css']
})
export class UserRatingDialogComponent implements OnInit{

  ratingForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<UserRatingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public message: string,
    private fb: FormBuilder  
  ) { }

  ngOnInit(){
    this.ratingForm = this.fb.group({
      star: new FormControl(1)
    })
  }
    
  onNoClick(): void {
    this.dialogRef.close(null);
  }

  rateUser() {
    // console.log(this.ratingForm.get("star").value);
    this.dialogRef.close(this.ratingForm.get("star").value);
  }
}
