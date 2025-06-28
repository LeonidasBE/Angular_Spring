import { ActivatedRoute, Router } from '@angular/router';
import { TicketService } from './../../services/ticket.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Ticket } from '../../model/ticket.model';
import { SharedService } from '../../services/shared.service';
import { ResponseApi } from '../../model/response-api';
import { CommonModule } from '@angular/common';

@Component({
	selector: 'app-ticket-new',
		imports: [CommonModule, FormsModule],
	templateUrl: './ticket-new.component.html',
	styleUrl: './ticket-new.component.css'
})
export class TicketNewComponent {

	@ViewChild("form")
	form: NgForm = new NgForm([], []);

	ticket: Ticket = new Ticket('', 0, '', '', '', '', null, null, '', [], '');
	shared: SharedService;
	message : {text : string, type: string} = { text: '', type: ''};
	classCss: { [key: string]: boolean } = {};

	constructor(
		private ticketService: TicketService,
		private route: ActivatedRoute,
	) {
		this.shared = SharedService.getInstance();
	}

	ngOnInit() {
		let id: string = this.route.snapshot.params['id'];
		if(id != undefined) {
		this.findById(id);
		}
	}

	findById(id:string) {
		this.ticketService.findById(id).subscribe({
			next: (responseApi: ResponseApi) => {
				this.ticket = responseApi.data;
			},
			error: err => {
				this.showMessage({
					type: 'error',
					text: err['error']['errors'][0]
				});
			}
		});
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

	getFormGroupClass(isInvalid: boolean, isDirty:boolean): {} {
		return {
			'form-group': true,
			'has-error' : isInvalid  && isDirty,
			'has-success' : !isInvalid  && isDirty
		};
	}

	private newMessageText() {
		this.message = { text: '', type: ''};
	}

	register() {
		this.newMessageText();
		this.ticketService.createOrUpdate(this.ticket).subscribe({
			next: (responseApi: ResponseApi) => {
				this.ticket = new Ticket('', 0, '', '', '', '', null, null, '', [], '');
				let ticketRet : Ticket = responseApi.data;
				this.form.resetForm();
				this.showMessage({
					type: 'success',
					text: `Registered ${ticketRet.title} successfully`
				});
			},
			error: err => {
				this.showMessage({
					type: 'error',
					text: err['error']['errors'][0]
				});
			}
		});
	}

	onFileChange(event: any): void {
		if(event.target.files[0].size > 2000000) {
			this.showMessage({
				text: 'Maximum image size is 2 MB',
				type: 'error'
			})
		} else {
			this.ticket.image = '';
			let reader = new FileReader();
			reader.onloadend = (e: Event) => {
				this.ticket.image = reader.result as string;
			}
			reader.readAsDataURL(event.target.files[0]);
		}

	}

}
