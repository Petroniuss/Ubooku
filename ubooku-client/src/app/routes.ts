import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AuthenticationComponent } from './authentication/authentication.component';
import { SignUpComponent } from './authentication/sign-up/sign-up.component';
import { SignInComponent } from './authentication/sign-in/sign-in.component';
import { UploadComponent } from './upload/upload.component';
import { LibraryComponent } from './library/library.component';
import { BookDetailsComponent } from './book-details/book-details.component';

export const appRoutes : Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'auth', component: AuthenticationComponent},
    {
        path: 'share', component: UploadComponent
    },
    {
        path: 'library', component: LibraryComponent
    },
    {
        path: 'book-details/:id', component: BookDetailsComponent
    },
    { path: '', redirectTo: 'home', pathMatch: 'full' }
];