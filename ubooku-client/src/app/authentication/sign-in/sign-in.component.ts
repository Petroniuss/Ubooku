import { Component, OnInit, Input } from '@angular/core';
import { User } from 'src/app/shared/model/user';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthenticationService } from 'src/app/shared/service/authentication.service';
import { Logs } from 'selenium-webdriver';
import { Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';

import { FACEBOOK_AUTH_URL} from 'src/app/shared/urls';
import { fromEventPattern } from 'rxjs';

@Component({
  selector: 'sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {


  facebook_url = FACEBOOK_AUTH_URL;
  loading: boolean = false;

  constructor(private authService: AuthenticationService,
              private router: Router) { }

  ngOnInit() {
  }

  onSubmit(form: NgForm) {
    this.loading = true;
    this.authService.login(form.value).subscribe((res: any) => {
      this.authService.onSuccessfulLogin(res.body);
      this.loading = false;
      this.router.navigate(['/home']);
    }, (err: any) => {
      console.log("Failed to login");
      this.loading = false;
    });
    
  }

  facebookLogin() {
    
  }

}
