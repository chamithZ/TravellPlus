import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Reservation } from '../../Models/Reservation';
import { ContractService } from '../../Services/ContractService/contract.service';
import { ReservationService } from '../../Services/ReservationService/reservation.service';
import { RoomType } from '../../Models/RoomType';
import { Hotel } from '../../Models/Hotel';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  contract: any; // Define the type of contract according to your structure
  reservation: Reservation | null = null;
  paymentOption: boolean = true; // Default to full payment
  paymentDetails: any; 
  prepaymentAmount: number = 0;
  roomTypes:RoomType[]=[]
  hotel!:Hotel

  constructor(private route: ActivatedRoute, 
              private contractService: ContractService,
              private reservationService: ReservationService,
              private router: Router
          ) { }

  ngOnInit(): void {
    const state = history.state;
    console.log('State:', state);
    if (state && state.contractId) {
      const contractId = state.contractId;
      this.contractService.getContractById(contractId).subscribe(contract => {
        this.contract = contract.content;
      });
    }
    if (state && state.paymentDetails) {
      this.paymentDetails = state.paymentDetails;
    }
    if (state && state.reservation) {
      this.reservation = state.reservation;
    }
    if (state && state.roomTypes) {
      this.roomTypes = state.roomTypes;
      console.log(this.roomTypes)
    }
    if(state && state.hotel){
      this.hotel = state.hotel;
    }
  }

  updatePaymentOption(isFullPayment: boolean): void {
    this.paymentOption = isFullPayment;
    if (this.reservation) {
      this.reservation.fullPayment = isFullPayment;
      if (!isFullPayment) {
        // If prepayment is selected, calculate the prepayment amount
        this.prepaymentAmount = this.paymentDetails.totalPrice * (this.contract.prepaymentPercentage / 100);
      } else {
        this.prepaymentAmount = 0; // Reset prepayment amount if full payment is selected
      }
    }
  }

  payNow(): void {
    if (this.reservation) {
      // Add reservation using reservation service
      this.reservationService.addReservation(this.reservation).subscribe(res => {
        console.log('Reservation added successfully:', this.reservation);
        // Show SweetAlert success message
        Swal.fire({
          icon: 'success',
          title: 'Reservation Placed Successfully!',
          text: 'Your reservation has been successfully placed.',
          confirmButtonText: 'Go to Reservations Page',
        }).then((result) => {
          // Redirect to reservations page
          if (result.isConfirmed) {
            // Navigate to reservations page using router
            this.router.navigate(['/reservations']);
          }
        });
      }, error => {
        console.error('Error adding reservation:', error);
        // Handle error
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong! Please try again later.',
        });
      });
    }
  }
}
