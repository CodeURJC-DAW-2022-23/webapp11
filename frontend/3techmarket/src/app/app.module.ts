import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FooterComponent} from "./footer/footer.component";
import {HeaderComponent} from "./header/header.component";
import { HttpClientModule } from '@angular/common/http';
import {FormsModule} from "@angular/forms";
import {ProductService} from "./services/productsearch.service";
import { ProductSearchComponent } from './productsearch/productsearch.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ProductSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ProductService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
