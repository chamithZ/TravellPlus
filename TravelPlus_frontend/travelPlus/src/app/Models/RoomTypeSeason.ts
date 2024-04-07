import { Season } from "./Season";

export interface RoomTypeSeason{
    season:{
        "seasonId":number,
        startDate:string,
        endDate:string
        
    },
    seasons?: Season[];
    "roomPrice":number,
    "noOfRooms":number
}