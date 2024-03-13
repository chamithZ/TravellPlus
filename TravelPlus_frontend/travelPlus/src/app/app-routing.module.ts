import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddContractComponent } from './add-contract/add-contract.component';
import { AddHotelComponent } from './add-hotel/add-hotel.component';

const routes: Routes = [
  {path:'addContract',component: AddContractComponent},
  {path:'addHotel',component:AddHotelComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
