import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddContractComponent } from './Pages/add-contract/add-contract.component';
import { AddRoomTypeComponent } from './Pages/add-room-type/add-room-type.component';
import { HomeComponent } from './Pages/home/home.component';
import { AddHotelComponent } from './Pages/add-hotel/add-hotel.component';
import { AddSupplementComponent } from './Pages/add-supplement/add-supplement.component';
import { SearchListComponent } from './Pages/search-list/search-list.component';
import { HotelOverviewComponent } from './Pages/hotel-overview/hotel-overview.component';
import { LoginComponent } from './Pages/login/login.component';
import { ReservationsComponent } from './Pages/reservations/reservations.component';
import { UpdateHotelComponent } from './Pages/update-hotel/update-hotel.component';
import { UpdateContractComponent } from './Pages/update-contract/update-contract.component';
import { AdminDashboardComponent } from './Pages/admin-dashboard/admin-dashboard.component';
import { HotelListComponent } from './Pages/hotel-list/hotel-list.component';
import { UserRegisterComponent } from './Pages/user-register/user-register.component';

const routes: Routes = [
  {path:'reservations',component:ReservationsComponent},
  {path:'login',component:LoginComponent},
  {path:'register',component:UserRegisterComponent},
  {path:'adminDashboard',component:AdminDashboardComponent},
  {path:'addContract/:hotelId',component: AddContractComponent},
  {path:'addHotel',component:AddHotelComponent},
  {path:'updateHotel/:hotelId',component:UpdateHotelComponent},
  {path:'updateContract/:contractId',component:UpdateContractComponent},
  {path:'',component:HomeComponent},
  {path:'hotelList',component:HotelListComponent},
  {path:'hotel/:hotelId',component:HotelOverviewComponent},
  {path:'searchResult',component:SearchListComponent},
  {path:'addRoomType/:contractId',component:AddRoomTypeComponent},
  {path:'addSupplement/:contractId',component:AddSupplementComponent}
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
