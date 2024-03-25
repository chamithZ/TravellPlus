import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddHotelComponent } from './Components/add-hotel/add-hotel.component';
import { AddContractComponent } from './Components/add-contract/add-contract.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddRoomTypeComponent } from './Components/add-room-type/add-room-type.component';
import { AddSupplementComponent } from './Components/add-supplement/add-supplement.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './Components/header/header.component';
import { FooterComponent } from './Components/footer/footer.component';
import { HomeComponent } from './Pages/home/home.component';
import { HomeHeroComponent } from './Components/home-hero/home-hero.component';
import { HomeSearchBarComponent } from './Components/home-search-bar/home-search-bar.component';
import { HotelCardComponent } from './Components/hotel-card/hotel-card.component';

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

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
