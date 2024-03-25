import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddContractComponent } from './Components/add-contract/add-contract.component';
import { AddHotelComponent } from './Components/add-hotel/add-hotel.component';
import { AddRoomTypeComponent } from './Components/add-room-type/add-room-type.component';
import { HomeComponent } from './Pages/home/home.component';

const routes: Routes = [
  {path:'addContract',component: AddContractComponent},
  {path:'addHotel',component:AddHotelComponent},
  {path:'home',component:HomeComponent},
  {path:'addRoomType',component:AddRoomTypeComponent}
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
