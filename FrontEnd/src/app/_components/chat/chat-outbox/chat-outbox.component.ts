import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { MessageService, AuthenticationService, AlertService } from 'src/app/_services';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { ComposeDialogComponent } from '../compose-dialog/compose-dialog.component';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { Message } from 'src/app/_models/message/message';

@Component({
  selector: 'app-chat-outbox',
  templateUrl: './chat-outbox.component.html',
  styleUrls: ['./chat-outbox.component.css']
})
export class ChatOutboxComponent implements OnInit {

  outboxMessages: Message[];
  currentUser: User;
  loadedMessages: boolean;

  constructor(
    private messageService: MessageService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit() {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    this.loadedMessages = false;
    this.loadMessages();
  }

  loadMessages() {
    this.messageService.getOutbox(this.currentUser.publicId)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.outboxMessages = data;
        this.loadedMessages = true;
      },
      error => {
        this.alertService.error(error);
        this.loadedMessages = true;
    });
  }

  // DELETE

  deleteMessage(message: Message, index: number) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '600px',
      data: "Are you sure you want to delete this message from your outbox?"
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        this.outboxMessages[index].deletedFrom = true;
        this.messageService.deleteMessage(message.id, "sender")
        .pipe(first())
        .subscribe(
          data => {
            this.router.navigate(["/user/chat/outbox"]);
          },
          error=> {
            this.alertService.error(error);
          }
        );
      }
    });  
  }

}
