import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule }    from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

// used to create fake backend

import { AppComponent }  from './app.component';

import { AlertComponent } from './_components/alert';
import { JwtInterceptor, ErrorInterceptor } from './_helpers';
import { LoginComponent } from './_components/login';
import { RegisterComponent } from './_components/register';
import { UserService } from './_services/user.service';
import { HomeComponent } from './_components/home';
import { AppRoutingModule } from './app-routing.module';
import { AdminModule } from './_modules/admin.module';
import { UserModule } from './_modules/user.module';
import { AuthenticationService } from './_services';
import { FooterComponent } from './_components/footer/footer.component';
import { HeaderComponent } from './_components/header/header.component';
import { SharedModule } from './_modules/shared.module';
import { ItemModule } from './_modules/item.module';

@NgModule({
    imports: [
        // NgbModule,
        BrowserModule,
        ReactiveFormsModule,
        HttpClientModule,
        SharedModule,
        ItemModule,             ///////////////////
        AdminModule,         /////////////////
        UserModule,           /////////////////
        AppRoutingModule,        // App routing module must be the last one imported in here
    ],
    declarations: [
        AppComponent,
        // My app components
        HomeComponent,
        LoginComponent,
        RegisterComponent,
        FooterComponent,
        HeaderComponent
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
        UserService,
        AuthenticationService
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }