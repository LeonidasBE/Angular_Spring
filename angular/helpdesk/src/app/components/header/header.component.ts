import { Component } from '@angular/core';
import { SharedService } from '../../services/shared.service';
import { User } from '../../model/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  shared: SharedService;

  constructor() {
    this.shared = SharedService.getInstance();
    this.shared.user = new User('','','','',);
  }

  signOut(): void {
    this.shared.token = '';
    this.shared.user = new User('','','','',);
    window.location.href = '/login';
    window.location.reload();
  }
}
