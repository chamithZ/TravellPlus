import { RoomTypeSeason } from "./RoomTypeSeason";

export interface RoomType{
    selected: boolean;
    "roomId": number;
    "roomTypeName":string,
    "roomSize":string,
    "roomBedType":string,
    "roomDescription":string,
    "roomImage":string,
    "contractId":number,
    "roomTypeSeasons": RoomTypeSeason[],
    roomPrice?: number; // Add this property
    roomCount?: number;
    availableRoomsCount?: number;
    selectedRoomCount?: number;

}