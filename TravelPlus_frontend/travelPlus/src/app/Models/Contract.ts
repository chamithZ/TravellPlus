import { Offer } from "./Offer";
import { Season } from "./Season";

export interface contract{
    startDate:String,
    endDate:String,
    cancellationFeePercentage:number,
    cancellationDeadline:number,
    prepaymentPercentage:number,
    paymentDeadline:number,
    hotelId:number,
    season:Season[],
    offer:Offer[] 

}  