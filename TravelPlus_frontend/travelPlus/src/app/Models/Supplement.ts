import { SupplementSeason } from "./SupplementSeason";

export interface Supplement{
    "supplementName":string,
    "roomBedType":string,
    "roomDescription":string,
    "roomImage":string,
    "contractId":number,
    "roomTypeSeasons": SupplementSeason[]

}