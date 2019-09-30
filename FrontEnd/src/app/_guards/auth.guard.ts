// The auth guard is used to prevent unauthenticated users from 
// accessing restricted routes, in this example it's used 
// in app.routing.ts to protect the home page route. 

import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../_services/authentication.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        // console.log(route.data.role);
        const currentUser = this.authenticationService.currentUserValue;
        if (currentUser) {
            // console.log("current user exists : " + route.data.role);
            // authorised so return true
            if(route.data.role) {
                if (route.data.role===currentUser.role) return true;
                else this.router.navigate(['/forbidden']);
            }      
            else return false;  
        } 

        //if we get here that means that there is no logged user
        // but if the requested route has role dependency that anyone that is not logged in cannot access it
        // the roles can only be admin or site_user
        if(route.data.role) this.router.navigate(['/forbidden']);
        // console.log("current user doesnt exist : " + route.data.role);
        // not logged in so redirect to home page
        // this.router.navigate(['/'], { queryParams: { returnUrl: state.url }});
        // this.router.navigate(['/']);
        return false;
    }
}