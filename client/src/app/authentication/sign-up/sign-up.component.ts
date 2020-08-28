import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/shared/service/authentication.service';
import { NgForOf } from '@angular/common';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  loading: boolean = false;

  constructor(private authService: AuthenticationService,
              private router: Router) { }



  ngOnInit() {
  }

  onSubmit(form: NgForm) {
    this.loading = true;
      this.authService.registerUser(form.value)
      .subscribe((res: any) => {
        console.log(res.body.message);
        this.authService.login(form.value).subscribe((res: any) => {
          this.loading = false;
          this.authService.onSuccessfulLogin(res.body);
          this.router.navigate(['/home']);
        })
      }, (err: any) => {
        this.loading = false;
        console.log(err.error.message);
      })
  }

}
