import { Component, OnInit } from '@angular/core';
import { AuthService } from 'angularx-social-login';
import { AuthenticationService } from '../shared/service/authentication.service';
import { User } from '../shared/model/user';
import { isDefined } from '@angular/compiler/src/util';
import { ACCESS_TOKEN } from '../shared/urls';
import { TouchSequence } from 'selenium-webdriver';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  open: HTMLElement;
  changeIcon: boolean;
  public user: User;

  constructor(private auth: AuthenticationService) { }

  ngOnInit() {
    this.open = document.getElementById('hamburger');
    this.changeIcon = true;

    this.open.addEventListener("click", this.onHambuergerClick);

    if(this.isLogged()) {
      this.auth.fetchUser().subscribe((res: any) => {
        this.auth.rememberUser(res);
        this.user = this.auth.getUser();
      })
    }
  }

  onHambuergerClick() {
      var overlay = document.querySelector('.overlay');
      var nav = document.querySelector('nav');
      var icon = document.querySelector('.menu-toggle i');
  
      overlay.classList.toggle("menu-open");
      nav.classList.toggle("menu-open");
  
      if (this.changeIcon) {
          icon.classList.remove("fa-bars");
          icon.classList.add("fa-times");
  
          this.changeIcon = false;
      }
      else {
          icon.classList.remove("fa-times");
          icon.classList.add("fa-bars");
          this.changeIcon = true;
      }
  }

  isLogged() {
    if(this.auth.loggedIn() && this.user == null){
      this.auth.fetchUser().subscribe((res: any) => {
        this.auth.rememberUser(res);
        this.user = this.auth.getUser();
      });
    }
    return this.auth.loggedIn();
  }

  logout() {
    this.user = null;
    this.auth.logout();
  }


}
