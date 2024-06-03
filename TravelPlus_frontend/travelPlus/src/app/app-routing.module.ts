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
import { ReservationOverviewComponent } from './Pages/reservation-overview/reservation-overview.component';
import { HotelAdminRegisterComponent } from './Pages/hotel-admin-register/hotel-admin-register.component';
import { ContractListComponent } from './Pages/contract-list/contract-list.component';
import { DisabledContractListComponent } from './Pages/disabled-contract-list/disabled-contract-list.component';
import { PaymentComponent } from './Pages/payment/payment.component';
import { HotelAdminDashboardComponent } from './Pages/hotel-admin-dashboard/hotel-admin-dashboard.component';
import { AdminReservationListComponent } from './Pages/admin-reservation-list/admin-reservation-list.component';
import { ViewHotelAdminsComponent } from './Pages/view-hotel-admins/view-hotel-admins.component';
import { AuthErrorComponent } from './Components/auth-error/auth-error.component';
import { RoomTypeListComponent } from './Pages/room-type-list/room-type-list.component';
import { SupplementListComponent } from './Pages/supplement-list/supplement-list.component';
import { AccountSettingComponent } from './Pages/account-setting/account-setting.component';
import { UpdateUserComponent } from './Pages/update-user/update-user.component';
import { AboutusComponent } from './Pages/aboutus/aboutus.component';
import { ContactusComponent } from './Pages/contactus/contactus.component';

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
  {path:'contractList',component:ContractListComponent},
  {path:'reservationOverview/:reservationId',component:ReservationOverviewComponent},
  {path:'hotel/:hotelId',component:HotelOverviewComponent},
  {path:'searchResult',component:SearchListComponent},
  {path:'addRoomType/:contractId',component:AddRoomTypeComponent},
  {path:'addSupplement/:contractId',component:AddSupplementComponent},
  {path:'addHotelAdmin',component:HotelAdminRegisterComponent},
  {path:'disabledContracts',component:DisabledContractListComponent},
  {path:'payment',component:PaymentComponent},
  {path:'HotelAdminDashboard',component:HotelAdminDashboardComponent},
  {path:'hotelReservations/:hotelId',component:AdminReservationListComponent},
  {path:'adminList',component:ViewHotelAdminsComponent},
  {path:'roomTypeList/:hotelId',component:RoomTypeListComponent},
  {path:'supplementList/:hotelId',component:SupplementListComponent},
  {path:'error',component:AuthErrorComponent},
  {path:'account',component:AccountSettingComponent},
  {path:'updateUser',component:UpdateUserComponent},
  {path:'aboutus',component:AboutusComponent},
  {path:'contactus',component:ContactusComponent}
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
