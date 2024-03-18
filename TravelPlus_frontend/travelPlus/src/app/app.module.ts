import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddHotelComponent } from './Components/add-hotel/add-hotel.component';
import { AddContractComponent } from './Components/add-contract/add-contract.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddRoomTypeComponent } from './Components/add-room-type/add-room-type.component';
import { AddSupplementComponent } from './Components/add-supplement/add-supplement.component';

@NgModule({
  declarations: [
    AppComponent,
    AddHotelComponent,
    AddContractComponent,
    AddRoomTypeComponent,
    AddSupplementComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
