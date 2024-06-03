import { RoomType } from "./RoomType";
import { HotelImages } from "./HotelImages";
import { Offer } from "./Offer";

export interface Hotel{
    "hotelId": number,
    "hotelName": string,
    "hotelAddress": string,
    "hotelEmail": string,
    "hotelCity": string,
    "pricePerNight": number,
    "hotelContactNo": string,
    "hotelDescription": string,
    roomTypes?: RoomType[];
    hotelImages: HotelImages[];
    offers:Offer[]
}