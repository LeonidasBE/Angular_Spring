import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { MenuComponent } from './components/menu/menu.component';
import { FooterComponent } from './components/footer/footer.component';
import { SharedService } from './services/shared.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, MenuComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  
  title = 'helpdesk';
  showTemplate:boolean = false;
  public shared:SharedService;

  constructor() {
    this.shared = SharedService.getInstance();
    this.showTemplate = true;
  }

  ngOnInit() {
    this.shared.showTemplate.subscribe(
      show => this.showTemplate = show
    )
  }

}
