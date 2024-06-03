import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReservationService } from '../../Services/ReservationService/reservation.service';
import { Reservation } from '../../Models/Reservation';
import { ReservationRoomTypeService } from '../../Services/ReservationRoomType/reservation-room-type.service';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';
import { PaymentService } from '../../Services/PaymentService/payment.service'; 
import Swal from 'sweetalert2'; // Import SweetAlerts
import pdfMake from 'pdfmake/build/pdfmake'; // Import pdfMake library
import pdfFonts from 'pdfmake/build/vfs_fonts'; // Import pdfFonts library



pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-reservation-overview',
  templateUrl: './reservation-overview.component.html',
  styleUrls: ['./reservation-overview.component.css']
})
export class ReservationOverviewComponent implements OnInit {
  reservationId!: number;
  reservation!: Reservation;
  hotel!: Hotel;
  payment: any; // Define the payment variable to hold payment details

  constructor(private route: ActivatedRoute, 
              private reservationService: ReservationService, 
              private reservationRoomTypeService: ReservationRoomTypeService, 
              private hotelService: HotelService, 
              private paymentService: PaymentService, 
              private router: Router) { }

  ngOnInit(): void {
    // Get the reservationId from the route parameters
    this.route.params.subscribe(params => {
      this.reservationId = +params['reservationId']; // Assuming reservationId is a number
      // Call the service method to get the reservation details
      this.reservationService.getReservationById(this.reservationId).subscribe(response => {
        // Handle the response
        this.reservation = response.content; // Assuming response.content contains the Reservation object
        this.getRoomTypesAndHotelsForReservations();
        console.log(response.content);
        
        // Call the getPaymentByReservationId method to fetch payment details
        this.paymentService.getPaymentByReservationId(this.reservationId).subscribe(payment => {
          this.payment = payment.content;
          console.log('Payment:', payment.content);
        }, paymentError => {
          console.error('Error fetching payment:', paymentError);
        });
      }, error => {
        // Handle error
        console.error('Error fetching reservation:', error);
      });
    });
  }

  getRoomTypesAndHotelsForReservations(): void {
    // Fetch room types for the reservation
    this.reservationRoomTypeService.getReservationRoomType(this.reservationId)
      .subscribe((response: any) => {
        if (response && response.content) {
          this.reservation.reservationRoomTypes = response.content; // Assign room types to reservation
          console.log('Room types:', response.content);

          // Fetch hotel for the reservation
          this.hotelService.getHotelById(this.reservation.hotelId)
            .subscribe((hotelResponse: any) => {
              if (hotelResponse && hotelResponse.content) {
                const hotel = hotelResponse.content;
                this.hotel = hotel; // Store hotel in the hotels array
                console.log('Hotel:', hotel);
              } else {
                console.error('Invalid hotel response format:', hotelResponse);
              }
            }, hotelError => {
              console.error('Error fetching hotel:', hotelError);
            });
        } else {
          console.error('Invalid response format:', response);
        }
      }, error => {
        console.error('Error fetching room types for reservation:', error);
      });
  }

  cancelReservation(): void {
    // Display a SweetAlert confirmation dialog
    Swal.fire({
      title: 'Cancel Reservation',
      text: 'Are you sure you want to cancel this reservation?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, cancel it!',
      cancelButtonText: 'No, keep it'
    }).then((result) => {
      if (result.isConfirmed) {
        // User confirmed to cancel the reservation
        this.reservationService.deleteReservation(this.reservationId).subscribe(
          () => {
            // Show success alert
            Swal.fire('Cancelled!', 'Your reservation has been canceled.', 'success').then(() => {
              // Navigate to another page after cancellation, for example, back to reservations page
              this.router.navigate(['/reservations']);
            });
          },
          error => {
            // Show error alert
            Swal.fire('Error', 'Failed to cancel reservation.', 'error');
            console.error('Error canceling reservation:', error);
            // Handle error
          }
        );
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        // User clicked on "No, keep it"
        Swal.fire('Cancelled', 'Your reservation is safe :)', 'info');
      }
    });
  }
  
  payBalance(): void {
    if (confirm('Are you sure you want to pay the remaining balance?')) {
        // Call a method in your service to update the reservation's isFullPayment status
        this.reservationService.updateIsFullPayment(this.reservationId).subscribe(
            () => {
                Swal.fire('Payment Successful', 'Reservation is fully paid.', 'success'); // Display success alert
                this.router.navigate(['/reservations']);
            },
            error => {
                Swal.fire('Error', 'Failed to update payment.', 'error'); // Display error alert
                console.error('Error updating payment:', error);
                // Handle error
            }
        );
    }
}
generatePDF(): void {
  // Define styles
  const titleStyle = { fontSize: 24, bold: true, margin: [0, 0, 10, 0], alignment: 'center' };
  const subheaderStyle = { fontSize: 18, bold: true, margin: [0, 10, 5, 10] }; // Increased bottom margin
  const textStyle = { fontSize: 16, margin: [0, 0, 10, 10] }; // Increased bottom margin
  const travelPlusStyle = { fontSize: 24, bold: true, margin: [0, 0, 10, 0], alignment: 'center' };
  const blueText = { color: '#3B82F6' }; // Blue color
  const grayText = { color: '#6B7280' }; // Gray color

  // Define content
  const docDefinition: any = {
    content: [
      {
        text: [
          { text: 'Travel', style: { color: '#41A4FF' } }, // Blue color for "Travel"
          { text: 'Plus', style: { color: '#000000' } }    // Black color for "Plus"
        ],
        style: 'travelPlus'
      },
      { text: 'Reservation Details', style: 'subheader' },
      { text: 'Hotel: ' + this.hotel.hotelName, style: 'subheader' },
      { text: 'Check-in Date: ' + this.reservation.checkInDate, style: 'text' },
      { text: 'Check-out Date: ' + this.reservation.checkOutDate, style: 'text' },
      { text: 'Guest Count: ' + this.reservation.guestCount, style: 'text' },
      { text: 'Full Payment: ' + (this.reservation.fullPayment ? 'Yes' : 'No'), style: 'text' },
      { text: 'Reserved Room Details', style: 'subheader' },
      // Add room type details here...
      { text: 'Total Payment: LKR ' + this.payment.totalPrice, style: 'text' },
      { text: 'Reservation Id : ' + this.reservationId, style: 'text' },
      // Add remaining payment details here if necessary...
      { text: 'Thank you for choosing us! We hope you had a pleasant stay. We look forward to serving you again soon.', style: ['text', blueText] },
      { text: 'To reserve again or for any inquiries, please contact us at ' + this.hotel.hotelEmail + ' or ' + this.hotel.hotelContactNo, style: ['text', grayText] },
    ],
    styles: {
      title: titleStyle,
      subheader: subheaderStyle,
      text: textStyle,
      travelPlus: travelPlusStyle,
    },
    pageMargins: [40, 60, 40, 60], // Add margins: top, left, bottom, right
    pageSize: 'A4' // Set page size to A4
  };

  pdfMake.createPdf(docDefinition).download('TravelPlus_Invoice.pdf');
}
shouldDisplayCancellationPercentage(): boolean {
  const today = new Date();
  const checkoutDate = new Date(this.reservation.checkOutDate); // Assuming checkout date is in valid format
  const cancellationDeadline = this.payment.rcancellationDeadline; // Assuming rcancellationDeadline is a number of days

  // Calculate the cancellation deadline date by subtracting the cancellation deadline number of days from the checkout date
  const cancellationDeadlineDate = new Date(checkoutDate);
  cancellationDeadlineDate.setDate(cancellationDeadlineDate.getDate() - cancellationDeadline);

  // Return true if today's date is less than or equal to the cancellation deadline date
  return today >= cancellationDeadlineDate;
}




}
