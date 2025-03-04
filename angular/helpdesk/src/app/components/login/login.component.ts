import { UserService } from './../../services/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user.model';
import { SharedService } from '../../services/shared.service';
import { Router } from '@angular/router';
import { CurrentUser } from '../../model/current-user.model';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  user = new User('','','','');
  shared : SharedService;
  message : string = '';

  constructor(private userService: UserService,
              private router: Router) { 
    this.shared = SharedService.getInstance();
  }

  ngOnInit() {
  }

  login(){
    this.message = '';
    this.userService.login(this.user).subscribe((userAuthentication:CurrentUser) => {
        this.shared.token = userAuthentication.token;
        this.shared.user = userAuthentication.user;
        this.shared.user.profile = this.shared.user.profile.substring(5);
        this.shared.showTemplate.emit(true);
        this.router.navigate(['/']);
    } , err => {
      this.shared.token = '';
      this.shared.user = new User('','','','');
      this.shared.showTemplate.emit(false);
      this.message = 'Error';
    });
  }

  cancelLogin(){
    this.message = '';
    this.user = new User('', '','','');
    window.location.href = '/login';
    window.location.reload();
  }

  getFormGroupClass(isInvalid: boolean, isDirty:boolean): {} {
    return {
      'form-group': true,
      'has-error' : isInvalid  && isDirty,
      'has-success' : !isInvalid  && isDirty
    };
  }

}
