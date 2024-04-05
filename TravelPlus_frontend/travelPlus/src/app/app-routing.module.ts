import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddContractComponent } from './Pages/add-contract/add-contract.component';
import { AddRoomTypeComponent } from './Pages/add-room-type/add-room-type.component';
import { HomeComponent } from './Pages/home/home.component';
import { AddHotelComponent } from './Pages/add-hotel/add-hotel.component';
import { AddSupplementComponent } from './Pages/add-supplement/add-supplement.component';
import { SearchListComponent } from './Pages/search-list/search-list.component';
import { HotelOverviewComponent } from './Pages/hotel-overview/hotel-overview.component';

const routes: Routes = [
  {path:'addContract/:hotelId',component: AddContractComponent},
  {path:'addHotel',component:AddHotelComponent},
  {path:'home',component:HomeComponent},
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
