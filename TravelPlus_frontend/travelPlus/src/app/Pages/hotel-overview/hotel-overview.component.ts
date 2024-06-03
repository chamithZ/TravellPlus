import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';
import { Response } from '../../Models/Response';
import { SearchCriteria } from '../../Models/SearchCriteria';
import { ContractService } from '../../Services/ContractService/contract.service';
import { RoomService } from '../../Services/RoomService/room.service';
import { RoomType } from '../../Models/RoomType';
import { Supplement } from '../../Models/Supplement';
import { SupplementService } from '../../Services/SupplementService/supplement.service';
import { OffersService } from '../../Services/OfferService/offers.service'; // Import OffersService
import { Offer } from '../../Models/Offer'; // Import Offer model
import { Reservation } from '../../Models/Reservation';
import { ReservationSupplement } from '../../Models/ReservationSupplements';
import { RoomTypeReservation } from '../../Models/RoomTypeReservation';
import { ReservationOffer } from '../../Models/ReservationOffer';
import { ReservationService } from '../../Services/ReservationService/reservation.service';
import { SeasonService } from '../../Services/SeasonService/season.service';
import { Season } from '../../Models/Season';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-hotel-overview',
  templateUrl: './hotel-overview.component.html',
  styleUrls: ['./hotel-overview.component.css']
})
export class HotelOverviewComponent implements OnInit {
  hotel: Hotel | null = null;
  searchCriteria: SearchCriteria | null = null;
  roomTypes: RoomType[] = [];
  selectedRoomTypes: RoomType[] = [];
  selectedSupplements: Supplement[] = [];
  supplements: Supplement[] = [];
  roomTypeMapping: { [key: number]: RoomType } = {};
  offers: Offer[] = []; // Offers array to store fetched offers
  numberOfNights!: number;
  totalPrice: number = 0;
  contractId!: number ; 
  imageUrls: string[] = [];
  season!:Season

  newReservation: Reservation = {
    checkInDate: '',
    checkOutDate: '',
    guestCount: 0,
    fullPayment: false,
    userId: 0,
    roomTypeReservation: [],
    reservationRoomTypes:[],
    paymentDTO: { rmarkUpPercentage: 0.0, rcancellationDeadline: 0, rpaymentDeadline: 0, rcancellationPercentage: 0.0,rprepaymentPercentage:0.0, totalPrice: 0.0 },
    reservationSupplementDTOS: [],
    reservationOffersDTOS: [],
    hotelId: this.hotel?.hotelId ||0
  }; 


  constructor(
    private route: ActivatedRoute,
    private router:Router,
    private hotelService: HotelService,
    private contractService: ContractService,
    private roomService: RoomService,
    private supplementService: SupplementService,
    private reservationService: ReservationService,
    private offersService: OffersService, // Inject OffersService
    private seasonService: SeasonService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const hotelId = +params['hotelId'];
      this.hotelService.getHotelById(hotelId).subscribe((response: Response<Hotel>) => {
        this.hotel = response?.content;
        console.log('Hotel:', this.hotel);
        
      });
      

      this.route.queryParams.subscribe(queryParams => {
        if (queryParams && queryParams['searchCriteria']) {
          this.searchCriteria = JSON.parse(queryParams['searchCriteria']) as SearchCriteria;
          console.log('Search Criteria:', this.searchCriteria);

          if (this.searchCriteria) {
            this.contractService.getContractIdByHotelIdAndDateRange(hotelId, this.searchCriteria.checkInDate, this.searchCriteria.checkOutDate)
              .subscribe(contract => {
                console.log('Contract:', contract.content);
                this.contractId = contract.content;

                // Check if contract and contract.content are not null
                if (contract && contract.content) {
                  this.roomService.getAvailableRoomTypes(contract.content, this.searchCriteria!.checkInDate, this.searchCriteria!.checkOutDate)
                    .subscribe(response => {
                      console.log('Available Room Types Response:', response); // Log room types response for debugging
                      if (response && Array.isArray(response)) {
                        // Assign room types after filtering by season
                        this.roomTypes = this.filterRoomTypesBySeason(response, this.searchCriteria!.checkInDate, this.searchCriteria!.checkOutDate);
                        // Populate roomTypeMapping for available rooms count assignment
                        this.roomTypes.forEach(roomType => {
                          this.roomTypeMapping[roomType.roomId] = roomType;
                        });

                        // Fetch available rooms count and assign to corresponding room type
                        this.roomService.getAvailableRoomCount(contract.content, this.searchCriteria!.checkInDate, this.searchCriteria!.checkOutDate)
                          .subscribe(countResponse => {
                            console.log('Available Rooms Count Response:', countResponse); // Log count response for debugging
                            if (Array.isArray(countResponse)) {
                              countResponse.forEach(item => {
                                const roomId = item?.[0]; // Use optional chaining here
                                const count = item?.[1]; // Use optional chaining here
                                console.log('Room ID:', roomId, 'Count:', count); // Log room ID and count for debugging
                                const roomType = this.roomTypeMapping[roomId];
                                if (roomType) {
                                  // Assign room count directly
                                  roomType.availableRoomsCount = count ?? 0; // Use nullish coalescing operator here
                                } else {
                                  console.log('Room type not found for ID:', roomId); // Log if room type is not found
                                }
                              });
                            } else {
                              console.log('Invalid response format for available rooms count:', countResponse);
                            }
                          }, error => {
                            console.error('Error fetching available rooms count:', error); // Log any errors during the API call
                          });
                          this.supplementService.getSupplementList(contract.content)
                          .subscribe(response => {
                            if (response && Array.isArray(response.content)) {
                              this.supplements = this.filterSupplementsBySeason(response.content, this.searchCriteria!.checkInDate, this.searchCriteria!.checkOutDate);
                              console.log(this.supplements)
                            } else {
                              console.log('Invalid supplement response format:', response);
                            }
                          });

                        // Fetch offers for the contract
                        this.offersService.getAllOffersForContract(contract.content)
                          .subscribe(offersResponse => {
                            if (offersResponse && Array.isArray(offersResponse.content)) {
                              this.offers = offersResponse.content;
                              console.log('Offers:', this.offers); // Log fetched offers
                            } else {
                              console.log('Invalid offer response format:', offersResponse);
                            }
                          }, error => {
                            console.error('Error fetching offers:', error); // Log any errors during offer fetch
                          });
                      } else {
                        console.log('Invalid response format for room types:', response);
                      }
                    });
                    this.getSeasonForReservation(contract.content, this.searchCriteria!.checkInDate, this.searchCriteria!.checkOutDate)
                    
                } else {
                  console.log('Contract or contract content is null.');
                }
              });
          }
        }
      });
    });

  }
  

  getSeasonForReservation(contractId: number, checkInDate: string, checkOutDate: string): void {
    this.seasonService.getSeasonForReservation(contractId, checkInDate, checkOutDate)
      .subscribe(seasons => {
        // Handle the fetched seasons here
        this.season=seasons.content;
        console.log('Fetched season:', seasons);
      }, error => {
        console.error('Error fetching seasons:', error);
      });
    }

  addReservation(): void {
    // Set reservation data based on user inputs and selected data
    this.newReservation.checkInDate = this.searchCriteria!.checkInDate;
    this.newReservation.checkOutDate = this.searchCriteria!.checkOutDate;
    this.newReservation.guestCount = this.searchCriteria!.guestCount;
    this.newReservation.fullPayment = true;
    this.newReservation.userId = Number(localStorage.getItem('userId'))!;
    
    if (this.hotel !== null) {
      this.newReservation.hotelId = this.hotel.hotelId;
    }
  
    // Fetch contract data and set paymentDTO based on contract details
    this.contractService.getContractById(this.contractId).subscribe(contract => {
      if (contract && contract.content) {
        const contractData = contract.content;
        this.newReservation.paymentDTO = {
          rmarkUpPercentage: this.season.markUp,
          rcancellationDeadline: contractData.cancellationDeadline,
          rprepaymentPercentage: contractData.prepaymentPercentage,
          rpaymentDeadline: contractData.cancellationDeadline,
          rcancellationPercentage: contractData.cancellationFeePercentage,
          totalPrice: this.calculateTotalPrice(),
        };
  
        // Prepare roomTypeReservation, supplements, and offers
        this.prepareReservationData();
      }
    }, error => {
      console.error('Error fetching contract details:', error);
    });
  }
  
  prepareReservationData(): void {
    // Prepare roomTypeReservation data based on selected room types and counts
    this.selectedRoomTypes.forEach(selectedRoomType => {
      const roomTypeReservation: RoomTypeReservation = {
        roomType: { roomId: selectedRoomType.roomId },
        roomCount: selectedRoomType.selectedRoomCount || 1,
        roomPrice: selectedRoomType.roomPrice || 0
      };
      this.newReservation.roomTypeReservation.push(roomTypeReservation);
    });
  
    // Prepare reservationSupplementDTOS based on selected supplements and prices
    this.selectedSupplements.forEach(selectedSupplement => {
      const reservationSupplement: ReservationSupplement = {
        rSupplementId: selectedSupplement.supplementId || 0,
        price: selectedSupplement.price || 0,
        serviceName: selectedSupplement.serviceName || ''
      };
      this.newReservation.reservationSupplementDTOS.push(reservationSupplement);
    });
  
    // Prepare reservationOffersDTOS based on selected offers
    this.offers.forEach(selectedOffer => {
      const reservationOffer: ReservationOffer = {
        rOfferId: selectedOffer.offerId || 0,
        offerName: selectedOffer.offerName || '',
        discountPercentage: selectedOffer.discountPercentage || 0
      };
      this.newReservation.reservationOffersDTOS.push(reservationOffer);
    });
    const paymentDetails = {
      roomTypeTotalPrice: this.calculateRoomTypeTotalPrice(),
      supplementsTotalPrice: this.calculateSupplementsTotalPrice(),
      totalDiscount: this.calculateTotalDiscount(),
      totalPrice: this.calculateTotalPrice(),
      serviceCharge: this.calculateServiceCharge(),
      totalAmountIncludingServiceCharge: this.calculateTotalAmountIncludingServiceCharge()
    };
    if (!localStorage.getItem('userId')){
      // Display SweetAlert for message
      Swal.fire({
        icon: 'warning',
        title: 'Oops...',
        text: 'You need to log in to view details!',
        confirmButtonText: 'Login'
      }).then((result) => {
        if (result.isConfirmed) {
          // Redirect to login page
          this.router.navigate(['/login']);
        }
      });
    }
  
    this.router.navigate(['/payment'], { state: { reservation: this.newReservation,paymentDetails:paymentDetails,contractId:this.contractId,roomTypes:this.selectedRoomTypes,hotel:this.hotel} });


 
  }
  
  
  // Helper method to filter room types based on season and set room price and count
  filterRoomTypesBySeason(roomTypes: RoomType[], checkInDate: string, checkOutDate: string): RoomType[] {
    const filteredRoomTypes: RoomType[] = [];
    roomTypes.forEach(roomType => {
      const validSeasons = roomType.roomTypeSeasons.filter(season => {
        return new Date(checkInDate) >= new Date(season.season.startDate) && new Date(checkOutDate) <= new Date(season.season.endDate);
      });
      if (validSeasons.length > 0) {
        const selectedSeason = validSeasons[0];
        roomType.roomPrice = selectedSeason.roomPrice; // Assign room price
        filteredRoomTypes.push(roomType);
      }
    });
    return filteredRoomTypes;
  }
  getRoomCountOptions(availableRoomsCount: number | undefined): number[] {
    const count = availableRoomsCount ?? 0; // Use nullish coalescing operator to handle undefined
    this.calculateTotalPrice();
    return Array.from({ length: count }, (_, index) => index + 1); // Generate an array from 1 to count
  }
  filterSupplementsBySeason(supplements: Supplement[], checkInDate: string, checkOutDate: string): Supplement[] {
    const filteredSupplements: Supplement[] = [];
    supplements.forEach(supplement => {
      const validSeason = supplement.supplementSeason.find(season => {
        const startDate = new Date(season.season.startDate);
        const endDate = new Date(season.season.endDate);
        const checkIn = new Date(checkInDate);
        const checkOut = new Date(checkOutDate);
        return checkIn >= startDate && checkOut <= endDate;
      });
      if (validSeason) {
        // Assign the supplement price based on the valid season
        supplement.price = validSeason.price;
        filteredSupplements.push(supplement);
      }
    });
    return filteredSupplements;
  }
  calculateTotalPrice(): number {
    let totalPrice = 0;
    if (this.hotel && this.searchCriteria && this.searchCriteria.checkInDate && this.searchCriteria.checkOutDate) {
      const checkInDate = new Date(this.searchCriteria.checkInDate);
      const checkOutDate = new Date(this.searchCriteria.checkOutDate);
      const timeDifference = checkOutDate.getTime() - checkInDate.getTime();
      this.numberOfNights = Math.ceil(timeDifference / (1000 * 3600 * 24));

      

      // Calculate total price only for selected room types
      this.selectedRoomTypes.forEach(roomType => {
        if (roomType.selectedRoomCount && roomType.roomPrice) {
          totalPrice += roomType.selectedRoomCount * roomType.roomPrice * this.numberOfNights;
        }
      });

      // Calculate supplement costs
      this.selectedSupplements.forEach(supplement => {
        if (supplement.price) {
          totalPrice += supplement.price * this.numberOfNights;
        }
      });

      // Apply offer discounts
      this.offers.forEach(offer => {
        if (offer.discountPercentage) {
          totalPrice -= (totalPrice * offer.discountPercentage) / 100;
        }
      });
     
     
    }
    return totalPrice;
  }

  addSelectedRoomType(roomType: RoomType): void {
    const index = this.selectedRoomTypes.findIndex(rt => rt.roomId === roomType.roomId);
    if (index === -1) {
      this.selectedRoomTypes.push(roomType);
    }
  }
  
  removeSelectedRoomType(roomType: RoomType): void {
    const index = this.selectedRoomTypes.findIndex(rt => rt.roomId === roomType.roomId);
    if (index !== -1) {
      this.selectedRoomTypes.splice(index, 1);
    }
  }


  addSelectedSupplement(supplement: Supplement): void {
    const index = this.selectedSupplements.findIndex(s => s.serviceName === supplement.serviceName);
    if (index === -1) {
      this.selectedSupplements.push(supplement);
    }
  }
  
  removeSelectedSupplement(supplement: Supplement): void {
    const index = this.selectedSupplements.findIndex(s => s.serviceName === supplement.serviceName);
    if (index !== -1) {
      this.selectedSupplements.splice(index, 1);
    }
  }
  handleRoomTypeSelection(event: any, roomType: RoomType): void {
    const checked = event.target?.checked;
    if (checked) {
      this.addSelectedRoomType(roomType);
    } else {
      this.removeSelectedRoomType(roomType);
    }
  }
  
  
  handleSupplementSelection(event: any, supplement: Supplement): void {
    const checked = event.target?.checked;
    if (checked) {
      this.addSelectedSupplement(supplement);
    } else {
      this.removeSelectedSupplement(supplement);
    }
  }
  isSelectedRoom(roomType: RoomType): boolean {
    return this.selectedRoomTypes.includes(roomType);
  }
  
  isSelectedSupplements(supplement: Supplement): boolean {
    return this.selectedSupplements.includes(supplement);
  }
  
  blobToUrl(blobData: any): string {
    try {
      const blob = new Blob([blobData], { type: 'image/jpeg' }); // Adjust the MIME type if needed
      return URL.createObjectURL(blob);
    } catch (error) {
      console.error('Error creating object URL:', error);
      return ''; // Return empty string or handle the error appropriately
    }
  }
  calculateRoomTypeTotalPrice(): number {
    let totalPrice = 0;
    this.selectedRoomTypes.forEach(roomType => {
      if (roomType.selectedRoomCount && roomType.roomPrice) {
        totalPrice += roomType.selectedRoomCount * roomType.roomPrice;
      }
    });
    return totalPrice;
  }
  
  
  calculateSupplementsTotalPrice(): number {
    let totalPrice = 0;
    this.selectedSupplements.forEach(supplement => {
      if (supplement.price) {
        totalPrice += supplement.price;
      }
    });
    return totalPrice;
  }
  calculateNumberOfNights() {
    if (this.searchCriteria && this.searchCriteria.checkInDate && this.searchCriteria.checkOutDate) {
      const checkInDate = new Date(this.searchCriteria.checkInDate);
      const checkOutDate = new Date(this.searchCriteria.checkOutDate);
      const timeDifference = checkOutDate.getTime() - checkInDate.getTime();
      this.numberOfNights = Math.ceil(timeDifference / (1000 * 3600 * 24));
    }
  }
  calculateTotalDiscount(): number {
    let totalDiscount = 0;
    this.offers.forEach(offer => {
      if (offer.discountPercentage) {
        totalDiscount += (this.calculateRoomTypeTotalPrice() + this.calculateSupplementsTotalPrice()) * (offer.discountPercentage / 100);
      }
    });
    return totalDiscount;
  }
  calculateServiceCharge(): number {
    let serviceCharge = 0;
    if (this.hotel && this.season) {
      // Calculate service charge based on total amount and season markup percentage
      serviceCharge = (this.calculateTotalPrice() * this.season.markUp) / 100;
    }
    return serviceCharge;
  }
  
  calculateTotalAmountIncludingServiceCharge(): number {
    // Calculate total amount including service charge
    return this.calculateTotalPrice() + this.calculateServiceCharge();
  }
  
  isLoggedIn(): boolean {
    return !!localStorage.getItem('userId');
  }
  
  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }
  
  
} 