import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddHotelComponent } from './Pages/add-hotel/add-hotel.component';
import { AddContractComponent } from './Pages/add-contract/add-contract.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddRoomTypeComponent } from './Pages/add-room-type/add-room-type.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './Components/header/header.component';
import { FooterComponent } from './Components/footer/footer.component';
import { HomeComponent } from './Pages/home/home.component';
import { HomeHeroComponent } from './Components/home-hero/home-hero.component';
import { HomeSearchBarComponent } from './Components/home-search-bar/home-search-bar.component';
import { HotelCardComponent } from './Components/hotel-card/hotel-card.component';
import { AddSupplementComponent } from './Pages/add-supplement/add-supplement.component';
import { CommonModule } from '@angular/common';
import { SearchListComponent } from './Pages/search-list/search-list.component';
import { HotelOverviewComponent } from './Pages/hotel-overview/hotel-overview.component';
import { ReservationsComponent } from './Pages/reservations/reservations.component';
import { LoginComponent } from './Pages/login/login.component';
import { ReservationOverviewComponent } from './Pages/reservation-overview/reservation-overview.component';
import { AdminDashboardComponent } from './Pages/admin-dashboard/admin-dashboard.component';
import { UpdateHotelComponent } from './Pages/update-hotel/update-hotel.component';
import { UpdateContractComponent } from './Pages/update-contract/update-contract.component';
import { AdminHeaderComponent } from './Components/admin-header/admin-header.component';
import { HotelListComponent } from './Pages/hotel-list/hotel-list.component';
import { UserRegisterComponent } from './Pages/user-register/user-register.component';
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
import { Hero1Component } from './Components/hero-1/hero-1.component';
import { Hero3Component } from './Components/hero-3/hero-3.component';
import { AboutusComponent } from './Pages/aboutus/aboutus.component';
import { ContactusComponent } from './Pages/contactus/contactus.component';


@NgModule({
  declarations: [
    AppComponent,
    AddHotelComponent,
    AddContractComponent,
    AddRoomTypeComponent,
    AddSupplementComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    HomeHeroComponent,
    HomeSearchBarComponent,
    HotelCardComponent,
    SearchListComponent,
    HotelOverviewComponent,
    ReservationsComponent,
    LoginComponent,
    ReservationOverviewComponent,
    AdminDashboardComponent,
    UpdateHotelComponent,
    UpdateContractComponent,
    AdminHeaderComponent,
    HotelListComponent,
    UserRegisterComponent,
    HotelAdminRegisterComponent,
    ContractListComponent,
    DisabledContractListComponent,
    PaymentComponent,
    HotelAdminDashboardComponent,
    AdminReservationListComponent,
    ViewHotelAdminsComponent,
    AuthErrorComponent,
    RoomTypeListComponent,
    SupplementListComponent,
    AccountSettingComponent,
    UpdateUserComponent,
    Hero1Component,
    Hero3Component,
    AboutusComponent,
    ContactusComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
