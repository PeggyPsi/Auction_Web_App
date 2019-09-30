import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService, UserService, AlertService } from '../../_services';
import { PatternValidator } from '../../_custom-validators/pattern/pattern.validator';
import { PasswordMatchValidator } from '../../_custom-validators/password-match/password-match.validator';
import { UniqueUsernameValidator } from '../../_custom-validators/unique-username/unique-username.validator';
import { FixedLengthValidator } from '../../_custom-validators/afm-pattern/fixed-length.validator';
import { MatDialog } from '@angular/material';
import { VerificationDialogComponent } from './verification-dialog/verification-dialog.component';


@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;
    submitted = false;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
        private alertService: AlertService,
        private dialog: MatDialog
    ) { 
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) { 
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        this.registerForm = this.formBuilder.group(
            {
                fname: ['', Validators.required],
                lname: ['', Validators.required],
                username: ['', Validators.compose([
                        // 1. Password Field is Required
                        Validators.required,
                        // 2. Username must be unique
                        UniqueUsernameValidator.uniqueUsernameValidator(this.userService)
                    ])
                ],
                password: ['', Validators.compose([
                        // 1. Password Field is Required
                        Validators.required, 
                        // 2. check whether the entered password has a number
                        PatternValidator.patternValidator(/\d/, { hasNumber: true }),
                        // 3. check whether the entered password has upper case letter
                        PatternValidator.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
                        // 4. check whether the entered password has a lower-case letter
                        PatternValidator.patternValidator(/[a-z]/, { hasSmallCase: true }),
                        // 5. check whether the entered password has a special character
                        PatternValidator.patternValidator(/[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/, { hasSpecialCharacters: true }),
                        // 6. Has a minimum length of 8 characters
                        Validators.minLength(8) 
                    ])
                ],
                pwConfirm: ['', Validators.required],
                phone: ['', Validators.compose([
                        // field is required
                        Validators.required,
                        Validators.min(6851850000),
                        Validators.max(9099999999) 
                    ])
                ],
                afm: ['', Validators.compose([
                        // field is required
                        Validators.required,
                        // Check whether the entered afm is a number
                        PatternValidator.patternValidator(/^\d+$/, { numeric: true }),
                        // Check for Accepted afm pattern
                        PatternValidator.patternValidator(/^([0-2][0-9]|(3)[0-1])(((0)[0-9])|((1)[0-2]))([0-9]{2})([0-9]{5})$/, { afmPattern: true }),
                        // Has length of exactly 11 characters (number)
                        FixedLengthValidator.fixedLengthValidator(11)
                    ])
                ],
                email: ['', Validators.compose([
                        // field is required
                        Validators.required,
                        // Check whether the entered email is in valid form
                        Validators.email
                    ])
                ],
                location: this.formBuilder.group(
                {
                    location: ['', Validators.required],
                    // longitude: [''],
                    // latitude: [''],
                    country: ['', Validators.required]
                })
            },
            {
                // check whether password and confirm password match
                validators: PasswordMatchValidator.passwordMatchValidator
            }
        );
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }
    get floc() : FormGroup{
        return this.registerForm.controls['location'] as FormGroup;
    }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }

        this.loading = true;
        // console.log("onSubmit");
        this.userService.register(this.registerForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.alertService.success('Registration successful', true);
                    // this.router.navigate(['/verification']);
                    this.registrationSuccess();
                },
                error => {
                    this.alertService.error(error);
                    this.loading = false;
                });
    }

    registrationSuccess() {
        const dialogRef = this.dialog.open(VerificationDialogComponent, {
            width: '400px',
            data: ""
          });
      
          dialogRef.afterClosed().subscribe(result => {
              this.router.navigate(['/']);
          });
    }
}
