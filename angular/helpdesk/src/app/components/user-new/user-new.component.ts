import { UserService } from './../../services/user.service';
import { Component, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { User } from '../../model/user.model';
import { SharedService } from '../../services/shared.service';
import { ActivatedRoute } from '@angular/router';
import { ResponseApi } from '../../model/response-api';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-new',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-new.component.html',
  styleUrl: './user-new.component.css'
})
export class UserNewComponent {

  @ViewChild("form")
  form: NgForm = new NgForm([], []);
  user = new User('','','','',);
  shared: SharedService;
  message : {text : string, type: string} = { text: '', type: ''};
  classCss: { [key: string]: boolean } = {};

  constructor(private userService: UserService,
    private route : ActivatedRoute
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
    this.userService.findById(id).subscribe((responseApi: ResponseApi) => {
      this.user = responseApi.data;
      this.user.password = '';
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
  }

  register() {
    this.newMessageText();
    this.userService.createOrUpdate(this.user).subscribe((responseApi: ResponseApi) => {
      this.user = new User('','','','',);
      let userRet : User = responseApi.data;
      this.form.resetForm();
      this.showMessage({
        type: 'success',
        text: `Registered ${userRet.email} successfully`
      });
    }, err => {
        this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
        });
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

}
