import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService, AlertService, AppService, MessageService } from '../../_services';

@Component( {
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: [string, any];
    defaultUrl: string;
    message: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private alertService: AlertService
    ) {
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) { 
            // this.router.navigate(['/']);
            this.router.navigate([this.authenticationService.currentHomeValue]);
        }
        this.route.params
        .subscribe(params => {
          if(params['bid'] !== undefined && params['bid'] === 'true') {
            let itemId = params['id'];
            this.returnUrl = ["/user/items", itemId];
            this.message = "You must be logged in to place a bid";
          }
        });
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
        });

        // get return url from route parameters or default url 
        // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.loading = true;
        this.authenticationService.login(this.f.username.value, this.f.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    if(this.message) {
                        // this.appService.refreshPage(this.returnUrl[0], "");
                        this.router.navigate(this.returnUrl);
                    }
                    else {
                        // console.log(this.authenticationService.currentHomeValue);
                        //TODO check this
                        // this.appService.refreshPage(this.authenticationService.currentHomeValue, "");
                        this.router.navigate([this.authenticationService.currentHomeValue]);
                    }
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                }
            )
    }

    // onLoad() {
    //     this.alertService.error(this.message);
    // }
}
