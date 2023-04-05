import { Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProductSearchComponent } from '../productsearch/productsearch.component';

const routes: Routes = [
  { path: 'search/:product', component: ProductSearchComponent }
];

export const routing = RouterModule.forRoot(routes);
