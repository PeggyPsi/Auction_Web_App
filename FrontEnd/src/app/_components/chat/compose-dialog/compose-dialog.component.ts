import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-compose-dialog',
  templateUrl: './compose-dialog.component.html',
  styleUrls: ['./compose-dialog.component.css']
})
export class ComposeDialogComponent implements OnInit{

  messageForm: FormGroup;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<ComposeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) 
  { }

  ngOnInit(): void {
    console.log("HEY");
    console.log(this.data);
    this.messageForm = this.formBuilder.group({
      to: [[{'display': this.data.to, 'value': this.data.to}], Validators.compose([
          // 1. Bid Field is Required
          Validators.required
        ])
      ],
      message: ['', Validators.compose([
          // 1. Bid Field is Required
          Validators.required
        ])
      ]
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.messageForm.controls; }
    
  onNoClick(): void {
    this.dialogRef.close(null);
  }

  onSubmit(): void {
    this.submitted = true;

    // stop here if form is invalid
    if (this.messageForm.invalid) {
      return;
    }

    this.data.message = this.messageForm.get('message').value;
    // Create message model adn send it back
    this.dialogRef.close(this.data.message);
  }

}
