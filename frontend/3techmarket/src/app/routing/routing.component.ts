import { Routes, RouterModule } from '@angular/router';

import { ProductSearchComponent } from '../productsearch/productsearch.component';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';
import { HomeComponent } from "../home/home.component";
import { DashboardComponent } from "../dashboard/dashboard.component";
import { ProfileComponent } from "../profile/profile.component";
import { ForbiddenComponent } from "../forbidden/forbidden.component";
import { MessagesComponent } from "../messages/messages.component";
import { ChatsComponent } from "../chats/chats.component";
import { RecentordersComponent } from "../recentorders/recentorders.component";
import { CartComponent } from "../cart/cart.component";
import {ReviewHistoryComponent} from "../reviewHistory/review-history.component";
import {RecoverpasswordComponent} from "../recoverpassword/recoverpassword.component";
import {CodeComponent} from "../code/code.component";
import {PricehistoryComponent} from "../pricehistory/pricehistory.component";
import {WishlistComponent} from "../wishlist/wishlist.component";
import { CheckoutComponent } from "../checkout/checkout.component";
import {AddProductComponent} from "../add-product/add-product.component";
import {ProductComponent} from "../product/product.component";

const routes: Routes = [
  { path: 'search/:product', component: ProductSearchComponent },
  { path: 'login', component: LoginComponent },
  { path: 'code', component: CodeComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'forbidden', component: ForbiddenComponent},
  { path: 'messages', component: MessagesComponent},
  { path: 'chats', component: ChatsComponent},
  { path: 'messages/:id', component: MessagesComponent},
  { path: 'purchases', component: RecentordersComponent},
  { path: 'cart', component: CartComponent},
  { path: 'recoverpassword', component: RecoverpasswordComponent},
  {path: 'review-history/:id',component: ReviewHistoryComponent},
  {path: 'pricehistory/:id',component: PricehistoryComponent},
  {path: 'wishlist',component: WishlistComponent},
  {path: 'checkout',component: CheckoutComponent},
  {path: 'addProduct',component: AddProductComponent},
  {path:'product/:id', component: ProductComponent}
];

export const routing = RouterModule.forRoot(routes);
