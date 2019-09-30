import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthenticationService } from '../_services';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(
        private authenticationService: AuthenticationService,
        private router: Router
    ) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            let error = "";
            if (err.status === 401) {
                // auto logout if 401 response returned from api
                this.authenticationService.logout();
                location.reload(true);
                error = err.error.message || err.statusText;
            }
            else if(err.status === 0) {
                this.authenticationService.logout();
                error = "Server connection could not be established...";
            
                this.router.navigate(["/server_error"]);
                // return;
                // TODO maybe add simple return or something so that flashes dont happen
            }
            else {
                error = err.error.message || err.statusText;                
            }
            
            return throwError(error);
        }))
    }
}