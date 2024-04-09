import { Hotel } from "./Hotel";
import { PaymentDTO } from "./Payment";
import { ReservationOffer } from "./ReservationOffer";
import { ReservationSupplement } from "./ReservationSupplements";
import { RoomTypeReservation } from "./RoomTypeReservation";

export interface Reservation {
    checkInDate: string;
    checkOutDate: string;
    guestCount: number;
    isFullPayment: boolean;
    userId: number;
    roomTypeReservation: RoomTypeReservation[];
    paymentDTO: PaymentDTO;
    reservationSupplementDTOS: ReservationSupplement[];
    reservationOffersDTOS: ReservationOffer[];
    hotelId: number;
  }
   