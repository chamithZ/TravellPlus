import { SupplementSeason } from "./SupplementSeason";

export interface Supplement{
    selected: boolean;
    "supplementId":number,
    "serviceName":string,
    "price":number,

    "supplementSeason": SupplementSeason[]

}