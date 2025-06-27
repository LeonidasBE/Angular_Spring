import { Router } from '@angular/router';
import { DialogService } from '../../model/dialogService';
import { UserService } from '../../services/user.service';
import { SharedService } from './../../services/shared.service';
import { Component } from '@angular/core';
import { ResponseApi } from '../../model/response-api';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { User } from '../../model/user.model';

@Component({
  selector: 'app-user-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
	page: number = 0;
	count: number = 5;
	pages: Array<number> = [];
	shared: SharedService; 
	message : {text : string, type: string} = { text: '', type: ''};
	classCss: { [key: string]: boolean } = {};
	listUser: User[]= [];

	constructor(
			private dialogService: DialogService,
			private userService: UserService,
			private router: Router
	){
			this.shared = SharedService.getInstance();
	}

	ngOnInit() {
		this.findAll(this.page, this.count);
	}

	findAll(page: number, count: number) {
		this.userService.findAll(page, count).subscribe({
			next: (responseApi: ResponseApi) => {
				this.listUser = responseApi.data.content;
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
		this.router.navigate(['user-new', id]);
	}

	delete(id: string) {
		this.dialogService.confirm('Do you really want to delete this user?')
		.then((canDelete: boolean)=> {
			if(canDelete) {
				this.newMessageText();
				this.userService.delete(id).subscribe({
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
