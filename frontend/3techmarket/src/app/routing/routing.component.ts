import { Routes, RouterModule } from '@angular/router';

import { ProductSearchComponent } from '../productsearch/productsearch.component';
import { LoginComponent } from '../login/login.component';
import { HomeComponent} from "../home/home.component";
import {DashboardComponent} from "../dashboard/dashboard.component";
import { ProfileComponent } from "../profile/profile.component";
import { ForbiddenComponent } from "../forbidden/forbidden.component";
import { MessagesComponent} from "../messages/messages.component";
import { ChatsComponent} from "../chats/chats.component";
import { RecentordersComponent } from "../recentorders/recentorders.component";

const routes: Routes = [
  { path: 'search/:product', component: ProductSearchComponent },
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'forbidden', component: ForbiddenComponent},
  { path: 'messages', component: MessagesComponent},
  { path: 'chats', component: ChatsComponent},
  { path: 'messages/:id', component: MessagesComponent},
  { path: 'purchases', component: RecentordersComponent},
];

export const routing = RouterModule.forRoot(routes);
