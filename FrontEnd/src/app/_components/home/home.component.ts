import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { first } from 'rxjs/operators';
import { AuthenticationService, UserService } from '../../_services';
import { User } from 'src/app/_models/user';


@Component({ 
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

    ngOnInit() {}

    constructor() {}
    // currentUser: User;
    // currentUserSubscription: Subscription;
    // users: User[] = [];

    // constructor(
    //     private authenticationService: AuthenticationService,
    //     private userService: UserService
    // ) {
    //     this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
    //         this.currentUser = user;
    //     });
    // }

    // ngOnInit() {
    //     // this.loadAllUsers();
    // }

    // ngOnDestroy() {
    //     // unsubscribe to ensure no memory leaks
    //     this.currentUserSubscription.unsubscribe();
    // }

    // deleteUser(public_id: string) {
    //     this.userService.delete(public_id).pipe(first()).subscribe(() => {
    //         this.loadAllUsers()
    //     });
    // }

    // private loadAllUsers() {
    //     this.userService.getAll().pipe(first()).subscribe(users => {
    //         this.users = users;
    //     });
    // }
}