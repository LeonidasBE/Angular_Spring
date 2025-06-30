import { Component } from '@angular/core';
import { Ticket } from '../../model/ticket.model';
import { SharedService } from '../../services/shared.service';
import { DialogService } from '../../model/dialogService';
import { TicketService } from '../../services/ticket.service';
import { Router } from '@angular/router';
import { ResponseApi } from '../../model/response-api';

@Component({
  selector: 'app-ticket-list',
  imports: [],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.css'
})
export class TicketListComponent {

  assignedToMe:boolean = false;
  page: number = 0;
  count: number = 5;
  pages: Array<number> = [];
  shared: SharedService;
  message : {text : string, type: string} = { text: '', type: ''};
  classCss: { [key: string]: boolean } = {};
  listTicket: Ticket[] = [];
  ticketFilter = new Ticket('', null, '', '', '', '', null, null, '', [], '');

  constructor(
    private dialogService: DialogService,
    private ticketService: TicketService,
    private router: Router
  ) {
    this.shared = SharedService.getInstance();
  }

  ngOnInit() {
    this.findAll(this.page, this.count);
  }
  
  findAll(page: number, count: number) {
    this.ticketService.findAll(page, count).subscribe({
      next: (responseApi: ResponseApi) => {
        this.listTicket = responseApi.data.content;
        this.pages = new Array(responseApi.data.totalPages);
      }, 
      error: (err) => {
        this.showMessage({
          type: 'error',
          text: err['error']['errors'][0]
        });
      }
    })
  }

  edit(id: string) {
    this.router.navigate(['ticket-new', id]);
  }

  detail(id: string) {
    this.router.navigate(['ticket-detail', id]);
  }

  delete(id: string) {
    this.dialogService.confirm('Do you really want to delete this ticket?')
      .then((canDelete: boolean)=> {
        if(canDelete) {
          this.newMessageText();
          this.ticketService.delete(id).subscribe({
            next: (responseApi: ResponseApi) => {
              this.showMessage({
                type: 'success',
                text: 'Record deleted'
              });
              this.findAll(this.page, this.count);
            },
            error: err => {
              this.showMessage({
                type: 'error',
                text: err['error']['errors'][0]
              })
            }      
          })
        }
      })
  }

  find(): void {
    this.ticketService.findByParams(this.page, this.count, this.assignedToMe, this.ticketFilter).subscribe({
      next: (responseApi) => {
        this.ticketFilter. title = this.ticketFilter.title == 'uninformed' ? '' : this.ticketFilter.title;
        this.ticketFilter. number = this.ticketFilter.number != null && this.ticketFilter.number < 1 ? null : this.ticketFilter.number;
        this.listTicket = responseApi['data'][ 'totalPages'];

      },
      error: err => {
        this.showMessage({
          type: 'error',
          text: err['error']['errors'][0]
        })
      }
    })
  }

  cleanFilter() {
    this.assignedToMe = false;
    this.page = 0;
    this.count = 5;
    this.ticketFilter = new Ticket('', null, '', '', '', '', null, null, '', [], '');
    this.findAll(this.page, this.count);
  }

	setNextPage(event: any) {
		event.preventDefault();
		if(this.page+1 < this.pages.length) {
			this.page++;
			this.findAll(this.page, this.count);
		}
	}

	setPreviousPage(event: any) {
		event.preventDefault();
		if(this.page > 0) {
			this.page--;
			this.findAll(this.page, this.count);
		}
	}

	setPage(pageIndex: number, event: any) {
		event.preventDefault();
		this.page = pageIndex;
		this.findAll(this.page, this.count);
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
