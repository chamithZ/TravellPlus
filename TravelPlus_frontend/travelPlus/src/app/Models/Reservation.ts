import { Hotel } from "./Hotel";
import { PaymentDTO } from "./Payment";
import { ReservationOffer } from "./ReservationOffer";
import { ReservationRoomType } from "./ReservationRoomType";
import { ReservationSupplement } from "./ReservationSupplements";
import { RoomTypeReservation } from "./RoomTypeReservation";

export interface Reservation {
    checkInDate: string;
    checkOutDate: string;
    guestCount: number;
    fullPayment: boolean;
    userId: number;
    roomTypeReservation: RoomTypeReservation[];
    reservationRoomTypes:ReservationRoomType[];
    reservationSupplementDTOS: ReservationSupplement[];
    reservationOffersDTOS: ReservationOffer[];
    paymentDTO: PaymentDTO;
    hotelId: number
  }
   