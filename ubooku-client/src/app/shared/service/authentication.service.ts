import { Injectable } from '@angular/core';
import { URL_AUTH_LOGIN, URL_AUTH_REGISTER, URL_USER_INFO, ACCESS_TOKEN } from '../urls';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import * as moment from 'moment';
import { FacebookLoginProvider, AuthService } from 'angularx-social-login';
import { User } from '../model/user';
import { stringify } from '@angular/compiler/src/util';
import { headersToString } from 'selenium-webdriver/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  user: User;

  constructor(private http: HttpClient,
              private socialAuthService: AuthService) {}
  

  //----------------

  login(form) {
    return this.http.post(URL_AUTH_LOGIN, form, {observe: 'response'});
  }

  onSuccessfulLogin(res: any) {
    localStorage.setItem("accessToken", res.tokenType + " " + res.accessToken);
    this.fetchUser().subscribe((res: any) => {
      this.rememberUser(res)
      console.log("Logged in!");
      
    });
  }

  fetchUser() {
    return this.http.get(URL_USER_INFO, {'headers': this.fetchHeaders()})
  }

  logout() {
    console.log("Log out!");
    
    this.user = null;
    localStorage.removeItem(ACCESS_TOKEN);
  }

  loggedIn() {
    return localStorage.getItem("accessToken") !== null;
  }

  getUser() {
    return this.user;
  }

  rememberUser(res: any) {
    this.user = new User();
      this.user.email = res.email;
      this.user.firstName = res.firstName;
      this.user.lastName = res.lastName;
      this.user.provider = res.provider;
      this.user.imageUrl = res.imageUrl;
  }

  registerUser(form) {
    return this.http.post(URL_AUTH_REGISTER, form, {observe: 'response'})
  }

  fetchHeaders() {
    var headers = new HttpHeaders({
      'Authorization': localStorage.getItem("accessToken"),
      'Content-Type': 'application/json'
    });
    return headers;
  }

  //----------------
}
