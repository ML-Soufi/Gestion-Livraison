import { AddOrderComponent } from './views/add-order/add-order.component';
import { DeliverComponent } from './views/deliver/deliver.component';
import { OrderComponent } from './views/order/order.component';
import { UserComponent } from './views/user/user.component';
import { ProductComponent } from './views/product/product.component';
import { HomeComponent } from './views/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AfterAuthGuard } from './guards/after-auth.guard';
import { AuthGuard } from './guards/auth.guard';
import { DashboardComponent } from './views/dashboard/dashboard.component';
import { LoginComponent } from './views/login/login.component';
import { PagenotfoundComponent } from './views/pagenotfound/pagenotfound.component';

const routes: Routes = [
  { path: "", redirectTo: "/Login", pathMatch: "full"},
  { path:"Login", component: LoginComponent, canActivate : [AfterAuthGuard]}, // canActivate : [AfterAuthGuard]
  { path:"Dashboard", component: DashboardComponent, children : [
    { path:"", component: HomeComponent, canActivate : [AuthGuard]},
    { path:"Products", component: ProductComponent, canActivate : [AuthGuard]},  
    { path:"Orders", component: OrderComponent, canActivate : [AuthGuard],},
    { path: "Orders/AddOrder", component: AddOrderComponent, canActivate : [AuthGuard], },
    { path:"Users", component: UserComponent, canActivate : [AuthGuard]},
    { path:"Delivers", component: DeliverComponent, canActivate : [AuthGuard]},
  ], canActivate : [AuthGuard]}, // canActivate : [AuthGuard]
  { path: "**", component: PagenotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
