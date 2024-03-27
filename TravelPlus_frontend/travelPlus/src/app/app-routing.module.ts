import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddContractComponent } from './Pages/add-contract/add-contract.component';
import { AddRoomTypeComponent } from './Pages/add-room-type/add-room-type.component';
import { HomeComponent } from './Pages/home/home.component';
import { AddHotelComponent } from './Pages/add-hotel/add-hotel.component';
import { AddSupplementComponent } from './Pages/add-supplement/add-supplement.component';

const routes: Routes = [
  {path:'addContract',component: AddContractComponent},
  {path:'addHotel',component:AddHotelComponent},
  {path:'home',component:HomeComponent},
  {path:'addRoomType/:contractId',component:AddRoomTypeComponent},
  {path:'addSupplement/:contractId',component:AddSupplementComponent}
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
