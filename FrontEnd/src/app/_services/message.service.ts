import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Message } from '../_models/message/message';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = 'https://localhost:8443';
  }
  
  saveMessage(messageModel: Message) {
    return this.http.post<any>(this.url+'/messages', messageModel);
  }

  getInbox(userId: string) {
    return this.http.get<any>(this.url+'/messages/inbox/'+userId);
  }

  getOutbox(userId: string) {
    return this.http.get<any>(this.url+'/messages/outbox/'+userId);
  }

  setSeen(messageId: number) {
    return this.http.get<any>(this.url+'/messages/'+messageId+'/seen');
  }

  deleteMessage(messageId: number, forUser: string) {
    return this.http.delete<any>(this.url+'/messages/'+messageId+'/delete', {
      params: new HttpParams().set('for', forUser)
    });
  }

  getUnseenMessages(userId: string) {
    return this.http.get<any>(this.url+'/messages/unseenNum/'+ userId);
  }
}
