import { RoomType } from "./RoomType";

export interface Hotel{
    "hotelId": number,
    "hotelName": string,
    "hotelAddress": string,
    "hotelEmail": string,
    "hotelCity": string,
    "hotelContactNo": string,
    "hotelDescription": string,
    roomTypes?: RoomType[];
}