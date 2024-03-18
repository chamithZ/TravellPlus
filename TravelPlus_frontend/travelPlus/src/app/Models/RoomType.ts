import { RoomTypeSeason } from "./RoomTypeSeason";

export interface RoomType{
    "roomTypeName":string,
    "roomSize":string,
    "roomBedType":string,
    "roomDescription":string,
    "roomImage":string,
    "contractId":number,
    "roomTypeSeasons": RoomTypeSeason[]

}