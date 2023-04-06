import { Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProductSearchComponent } from '../productsearch/productsearch.component';
import { LoginComponent } from '../login/login.component';
import { HomeComponent} from "../home/home.component";

const routes: Routes = [
  { path: 'search/:product', component: ProductSearchComponent },
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent },
];

export const routing = RouterModule.forRoot(routes);
