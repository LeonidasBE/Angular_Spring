import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SharedService } from '../../services/shared.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
  imports: [RouterLink, CommonModule]
})
export class MenuComponent {

  shared: SharedService;

  constructor() {
    this.shared = SharedService.getInstance();
  }



}
