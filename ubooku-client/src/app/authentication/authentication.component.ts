import { Component, OnInit } from '@angular/core';
import { User } from '../shared/model/user';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {

  user: User;

  constructor() { }

  ngOnInit() {
  }

}
