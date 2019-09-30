import { Component, OnInit, Inject } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-verification-dialog',
  templateUrl: './verification-dialog.component.html',
  styleUrls: ['./verification-dialog.component.css']
})
export class VerificationDialogComponent implements OnInit {

  currentHome: String;

  constructor(private authenticationService: AuthenticationService,
    public dialogRef: MatDialogRef<VerificationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
  }

}
