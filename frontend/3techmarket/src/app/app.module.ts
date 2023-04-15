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
import {routing} from './routing/routing.component';
import {NgOptimizedImage} from "@angular/common";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProfileComponent } from './profile/profile.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { MessagesComponent } from './messages/messages.component';
import { ChatsComponent } from './chats/chats.component';
import { RecentordersComponent } from './recentorders/recentorders.component';
import { CartComponent } from './cart/cart.component';
import { ReviewHistoryComponent } from './reviewHistory/review-history.component';
import { RegisterComponent } from './register/register.component';
import { RecoverpasswordComponent } from './recoverpassword/recoverpassword.component';
import { CodeComponent } from './code/code.component';
import { PricehistoryComponent } from './pricehistory/pricehistory.component';
import { HighchartsChartModule } from 'highcharts-angular';
import { WishlistComponent } from './wishlist/wishlist.component';
import { AddProductComponent } from './add-product/add-product.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { CheckoutComponent } from './checkout/checkout.component';

// @ts-ignore
// @ts-ignore
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ProductSearchComponent,
    LoginComponent,
    HomeComponent,
    DashboardComponent,
    ProfileComponent,
    ForbiddenComponent,
    MessagesComponent,
    ChatsComponent,
    RecentordersComponent,
    CartComponent,
    ReviewHistoryComponent,
    RegisterComponent,
    RecoverpasswordComponent,
    CodeComponent,
    PricehistoryComponent,
    WishlistComponent,
    AddProductComponent,
    EditProductComponent,
    CheckoutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgOptimizedImage,
    routing,
    NgbModule,
    NgbDropdownModule,
    HighchartsChartModule
  ],
  providers: [
    ProductService
  ],
  bootstrap: [AppComponent]
})
// @ts-ignore
export class AppModule { }
