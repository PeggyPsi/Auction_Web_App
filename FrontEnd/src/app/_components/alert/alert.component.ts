import { Component, OnInit, OnDestroy, OnChanges } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertService } from '../../_services/alert.service';

@Component({
    selector: 'alert',
    templateUrl: 'alert.component.html',
    styleUrls: ['./alert.component.css']
})

export class AlertComponent implements OnInit, OnDestroy {

    private subscription: Subscription;
    message: any;

    constructor(private alertService: AlertService) { }

    ngOnInit() {
        this.subscription = this.alertService.getMessage().subscribe(message => { 
            this.message = message;
            console.log("HOY" + this.message); 
        });
    }

    ngOnDestroy() {
        if(this.subscription) this.subscription.unsubscribe();
    }

    clearMessage() {
        this.alertService.clearMessage();
    }

    // updateMessage() {
    //     // console.log("HEY");
    //     // this.alertService.clearMessage();
    //     this.subscription = this.alertService.getMessage().subscribe(message => { 
    //         this.message = message; 
    //     });
    //     console.log(this.message.text);
    // }


    // ngOnChanges(changes: import("@angular/core").SimpleChanges) {
    //     console.log("HEY");
    //     this.subscription = this.alertService.getMessage().subscribe(message => { 
    //         this.message = message; 
    //     });
    // }
}