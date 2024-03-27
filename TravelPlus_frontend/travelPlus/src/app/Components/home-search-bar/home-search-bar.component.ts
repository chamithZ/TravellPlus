import { Component, HostListener , Renderer2, ElementRef} from '@angular/core';

@Component({
  selector: 'app-home-search-bar',
  templateUrl: './home-search-bar.component.html',
  styleUrls: ['./home-search-bar.component.css']
})
export class HomeSearchBarComponent {
  city: string = '';
  checkInDate: string = '';
  checkOutDate: string = '';
  roomCount: number = 1; // Default to 1 room
  guestCount: number = 1; // Default to 1 guest
  dropdownOpen: boolean = false;

  currentDate: string = new Date().toISOString().split('T')[0];
  tomorrowDate: string = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().split('T')[0];

  constructor(private renderer: Renderer2, private el: ElementRef) {}

  ngOnInit() {
    this.toggleDropdown();
    this.renderer.listen('document', 'click', (event: MouseEvent) => {
      this.handleDocumentClick(event);
    });
  }

  handleDocumentClick(event: MouseEvent) {
    if (!this.el.nativeElement.contains(event.target)) {
      this.dropdownOpen = false;
    }
  }
 
  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  incrementRoomCount() {
    // Allow incrementing without a maximum limit
    this.roomCount++;
  }

  decrementRoomCount() {
    // Ensure room count is never less than 1
    if (this.roomCount > 1) {
      this.roomCount--;
    }
  }

  incrementGuestCount() {
    // Allow incrementing without a maximum limit
    this.guestCount++;
  }

  decrementGuestCount() {
    // Ensure guest count is never less than 1
    if (this.guestCount > 1) {
      this.guestCount--;
    }
  }
}
