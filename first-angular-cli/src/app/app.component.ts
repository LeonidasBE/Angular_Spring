import { Component } from '@angular/core';
import { User } from './user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  //declaração de variáveis
  title = 'Tasks';
  upperText: string = "alo alo ";
  lowerText: string = "TESTEAA ";
  percentual : number = 0.5;
  data : Date = new Date();
  dinheiro : number = 250.00;
  isAdmin: boolean = true;
  profile : number = 2;

  //como declarar objeto no angular
  user : User = {
    name : 'Bob',
    age : 25
  };
}
