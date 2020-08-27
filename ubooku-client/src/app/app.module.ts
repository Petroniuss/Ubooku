import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations' 
import { HttpClientModule }    from '@angular/common/http';
import { SocialLoginModule, AuthServiceConfig, FacebookLoginProvider } from 'angularx-social-login'

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import { SignInComponent } from './authentication/sign-in/sign-in.component';
import { SignUpComponent } from './authentication/sign-up/sign-up.component';


import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonModule } from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { appRoutes } from './routes';
import { AuthenticationService } from './shared/service/authentication.service';
import { UploadComponent } from './upload/upload.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { LibraryComponent } from './library/library.component';
import { SearchBoxComponent } from './library/search-box/search-box.component';
import { BookCardComponent } from './library/book-card/book-card.component';
import { HttpClientService } from './shared/service/http-client.service';
import { BookDetailsComponent } from './book-details/book-details.component';

let config = new AuthServiceConfig([
   {
      id: FacebookLoginProvider.PROVIDER_ID,
      provider: new FacebookLoginProvider("2130979020544743")
   }
])

export function provideConfig() {
   return config;
}


@NgModule({
   declarations: [
      AppComponent,
      NavbarComponent,
      HomeComponent,
      AuthenticationComponent,
      SignInComponent,
      SignUpComponent,
      UploadComponent,
      LibraryComponent,
      SearchBoxComponent,
      BookCardComponent,
      BookDetailsComponent
   ],
   imports: [
      BrowserModule,
      ReactiveFormsModule,
      BrowserAnimationsModule,
      HttpClientModule,
      SocialLoginModule,
      RouterModule.forRoot(appRoutes),
      FormsModule,
      MatTabsModule,
      MatButtonModule,
      MatFormFieldModule,
      MatSelectModule,
      MatInputModule,
      ReactiveFormsModule,
      PdfViewerModule
   ],
   providers: [
      AuthenticationService,
      {
         provide: AuthServiceConfig,
         useFactory: provideConfig
      },
      HttpClientService
   ],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
