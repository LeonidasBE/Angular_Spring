import { EventEmitter, Injectable } from '@angular/core';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  public static instance : SharedService;
  user: User = new User('', '', '', '');
  token: string = '';
  showTemplate = new EventEmitter<boolean>();

  constructor() {
    SharedService.instance = SharedService.instance || this;
  }

  public static getInstance() {
    if(!this.instance) {
      this.instance = new SharedService();
    }
    return this.instance;
  }

  isLoggedIn() : boolean {
    if(this.user == null){
      return false
    }
    return this.user.email != ''; 
  }

}
