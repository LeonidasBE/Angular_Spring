import { Component, OnInit } from '@angular/core';
import { Ticket } from '../../model/ticket.model';
import { SharedService } from '../../services/shared.service';
import { TicketService } from '../../services/ticket.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ResponseApi } from '../../model/response-api';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ticket-detail',
  imports: [CommonModule, FormsModule],
  templateUrl: './ticket-detail.component.html',
  styleUrl: './ticket-detail.component.css'
})
export class TicketDetailComponent implements OnInit {

  ticket: Ticket = new Ticket('', 0, '', '', '', '', null, null, '', [], '');
  shared: SharedService;
  message : {text : string, type: string} = { text: '', type: ''};
  classCss: { [key: string]: boolean } = {};

  constructor(
    private ticketService : TicketService,
    private router: ActivatedRoute,
  ) {
    this.shared = SharedService.getInstance();
  }

  ngOnInit() {
    let id : string = this.router.snapshot.params['id'];
    if(id != undefined) {
      this.findById(id);
    }
  }

  findById(id: string) {
    this.ticketService.findById(id).subscribe({
      next: (responseApi) => {
        this.ticket = responseApi.data;
        this.ticket.date = new Date(this.ticket.date).toISOString();
      },
      error: err => {
        this.showMessage({
          type: 'error',
          text: err['error']['errors'][0]
        });
      }
    });
  }

  changeStatus(status: string): void {
    this.ticketService.changeStatus(status, this.ticket).subscribe({
      next: (responseApi: ResponseApi) => {
        this.ticket = responseApi.data;
        this.ticket.date = new Date(this.ticket.date).toISOString();
        this.showMessage({
          type: 'success',
          text: 'Status changed successfully!'
       })
      },
      error: (err) => {
        this.showMessage({
          type: 'error',
          text: err['error']['errors'][0]
        });
      }
    })
  }

  private showMessage(message: {type: string, text: string}): void {
    this.message = message;
    this.buildClasses(message.type);
    setTimeout(() => {
      this.newMessageText();
    }, 3000);
  }

  private newMessageText(): void {
    this.message.text = '';
    this.classCss = {};
  }

  private buildClasses(type: string): void {
     this.classCss = {
       'alert': true
     }
     this.classCss['alert-'+type] =  true;
  }
}
