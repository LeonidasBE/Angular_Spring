import { CanActivate } from '@angular/router';
import {  Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/security/login/login.component';
import { AuthGuard } from './components/security/auth.guard';
import { UserNewComponent } from './components/user-new/user-new.component';
import { UserListComponent } from './components/user-list/user-list.component';

export const routes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent},
    { path: 'user-new', component: UserNewComponent, canActivate: [AuthGuard]},
    { path: 'user-new/:id', component: UserNewComponent, canActivate: [AuthGuard]},
    { path: 'user-list', component: UserListComponent, canActivate: [AuthGuard]},
    
]
