import { Component, OnInit } from '@angular/core';
import { Message } from 'src/app/_models/message/message';
import { MessageService, AuthenticationService, AlertService } from 'src/app/_services';
import { User } from 'src/app/_models/user';
import { first } from 'rxjs/operators';
import { MatDialog } from '@angular/material';
import { ComposeDialogComponent } from '../compose-dialog/compose-dialog.component';
import { Router } from '@angular/router';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-chat-inbox',
  templateUrl: './chat-inbox.component.html',
  styleUrls: ['./chat-inbox.component.css']
})
export class ChatInboxComponent implements OnInit {

  inboxMessages: Message[];
  currentUser: User;
  loadedMessages: boolean;

  constructor(
    private messageService: MessageService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private dialog: MatDialog,
    private router: Router
  ) { 
  }

  ngOnInit() {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    // this.inboxMessages = [];
    this.loadedMessages = false;
    this.loadMessages();
  }

  loadMessages() {
    this.messageService.getInbox(this.currentUser.publicId)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.clearMessage();
        this.inboxMessages = data;
        console.log(this.inboxMessages);
        this.loadedMessages = true;
      },
      error => {
        this.alertService.error(error);
        // this.inboxMessages = [];
        this.loadedMessages = true;
    });
  }

  messageSeen(message: Message, index: number) {
    // only if message is not already seen
    if(!message.seen) {
      this.inboxMessages[index].seen = true;
      this.messageService.setSeen(message.id)
      .pipe(first())
      .subscribe(
        data => {
          this.authenticationService.setNumMessages(this.authenticationService.numNewMessagesValue-1);
        },
        error => {
          this.alertService.error(error);
      });
    }
  }

  // REPLY

  sendMessage(to: User, fromRole: string){
    const dialogRef = this.dialog.open(ComposeDialogComponent, {
      width: '600px',
      data: {to: to.username, message: ""}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.saveMessage(to, this.currentUser, result, fromRole);
    });
  }

  saveMessage(to: User, from: User, message: string, fromRole: string) {
    let messageModel = new Message();
    let date  = new Date();
    messageModel.dateTime = new Date(date.toISOString().substr(0,10) + "T" + date.toTimeString().substr(0,5));
    messageModel.fromUser = from;
    messageModel.toUser = to;
    messageModel.id = null;
    messageModel.seen = false;
    if(fromRole === "buyer") messageModel.fromRole = "seller";
    else if(fromRole === "seller") messageModel.fromRole = "buyer";
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

  // DELETE

  deleteMessage(message: Message, index: number) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      width: '600px',
      data: "Are you sure you want to delete this message from your inbox?"
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        this.inboxMessages[index].deletedTo = true;
        this.messageService.deleteMessage(message.id, "receiver")
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
    });  
  }

}
