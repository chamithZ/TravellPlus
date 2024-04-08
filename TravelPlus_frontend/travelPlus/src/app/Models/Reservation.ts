import { PaymentDTO } from "./Payment";
import { ReservationOffer } from "./ReservationOffer";
import { ReservationSupplement } from "./ReservationSupplements";
import { RoomTypeReservation } from "./RoomTypeReservation";

export interface Reservation {
    checkInDate: string;
    checkOutDate: string;
    adultCount: number;
    isFullPayment: boolean;
    userId: number;
    roomTypeReservation: RoomTypeReservation[];
    paymentDTO: PaymentDTO;
    reservationSupplementDTOS: ReservationSupplement[];
    reservationOffersDTOS: ReservationOffer[];
  }
   