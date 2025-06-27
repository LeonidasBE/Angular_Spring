import { Router } from '@angular/router';
import { DialogService } from '../../model/dialogService';
import { UserService } from '../../services/user.service';
import { SharedService } from './../../services/shared.service';
import { Component } from '@angular/core';
import { ResponseApi } from '../../model/response-api';

@Component({
  selector: 'app-user-list',
  imports: [],
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
  listUser=[];

	constructor(
			private dialogService: DialogService,
			private userService: UserService,
			private router: Router,
			private sharedService: SharedService
	){
			this.shared = SharedService.getInstance();
	}

	ngOnInit() {
			this.findAll(this.page, this.count);
	}

	findAll(page: number, count: number) {
			this.userService.findAll(page, count).subscribe({
					next: (responseApi: ResponseApi) => {
							this.listUser = responseApi['data']['content'];
							this.pages = responseApi['data']['totalPages'];
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
						this.message = { text: '', type: ''};
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
