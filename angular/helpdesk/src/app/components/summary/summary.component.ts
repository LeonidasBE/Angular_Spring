import { Component, OnInit } from '@angular/core';
import { Summary } from '../../model/summary.model';
import { TicketService } from '../../services/ticket.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-summary',
  imports: [CommonModule],
  templateUrl: './summary.component.html',
  styleUrl: './summary.component.css'
})
export class SummaryComponent implements OnInit {

  summary: Summary = new Summary();
  message : {text : string, type: string} = { text: '', type: ''};
  classCss: { [key: string]: boolean } = {};

  constructor(
    private ticketService: TicketService,
  ) {

  }

  ngOnInit() { 
    this.ticketService.summary().subscribe({
      next: (responseApi)=>{
        this.summary = responseApi.data;
      },
      error: (err) =>{
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
			this.newMessageText()
		}, 3000)
	}
 
	private buildClasses(type: string) : void {
		this.classCss = {
			'alert' : true
		}
		this.classCss['alert-'+type] = true;
	}

	private newMessageText() {
		this.message = { text: '', type: ''};
	}
}
