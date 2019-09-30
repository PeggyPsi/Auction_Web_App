import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { map, first } from 'rxjs/operators';
import { User } from '../_models/user/user';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;
    public currentHomeSubject: BehaviorSubject<String>;
    public currentHome: Observable<String>
    public numNewMessagesSubject: BehaviorSubject<number>;
    public numNewMessages: Observable<number>

    constructor(
        private http: HttpClient,
        private messageService: MessageService
    ) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();

        this.currentHomeSubject = new BehaviorSubject<String>(localStorage.getItem('currentHome'));
        this.currentHome = this.currentHomeSubject.asObservable();
        if(localStorage.getItem('currentHome') === null) localStorage.setItem('currentHome', '/');
        this.currentHomeSubject = new BehaviorSubject<String>(localStorage.getItem('currentHome'));
        this.currentHome = this.currentHomeSubject.asObservable();

        this.numNewMessagesSubject = new BehaviorSubject<number>(+localStorage.getItem('numNewMessages'));
        this.numNewMessages = this.numNewMessagesSubject.asObservable();
        // if(localStorage.getItem('numNewMessages') === null) localStorage.setItem('currentHome', '0');
        // this.numNewMessagesSubject = new BehaviorSubject<number>(+localStorage.getItem('numNewMessages'));
        // this.numNewMessages = this.numNewMessagesSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    public get currentHomeValue(): String {
        return this.currentHomeSubject.value;
    }

    public get numNewMessagesValue(): number {
        return +this.numNewMessagesSubject.value;
    }

    public setNumMessages(num: number) {
        // console.log("Got messages: ", num.toString());
        localStorage.setItem('numNewMessages', num.toString());
        this.numNewMessagesSubject.next(num);
    }

    login(username: string, password: string) {
        return this.http.post<User>(`https://localhost:8443/authenticate`, { username, password })
        .pipe(map(data => {
                // login successful if there's a jwt token in the response
                if (data && data.jwtToken) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(data));
                    this.currentUserSubject.next(data);

                    this.messageService.getUnseenMessages(data.publicId)
                    .pipe(first())
                    .subscribe(
                      data => {
                        //   this.authenticationService.setNumMessages(data);
                        console.log("Got messages");
                        localStorage.setItem('numNewMessages', data.message);
                        this.numNewMessagesSubject.next(data.message);
                    });

                    if(data.role == 'ADMIN'){
                        localStorage.setItem('currentHome', '/admin');
                        this.currentHomeSubject.next('/admin');
                    }
                    else{
                        localStorage.setItem('currentHome', '/user');
                        this.currentHomeSubject.next('/user');
                    } 

                    return data;
                }
                else return null;
            }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);

        localStorage.setItem('currentHome', '/');
        this.currentHomeSubject.next('/');

        localStorage.removeItem('numNewMessages');
        this.numNewMessagesSubject.next(null);
    }
}