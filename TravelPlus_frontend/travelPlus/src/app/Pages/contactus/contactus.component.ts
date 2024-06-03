import { Component } from '@angular/core';

@Component({
  selector: 'app-contactus',
  templateUrl: './contactus.component.html',
  styleUrl: './contactus.component.css'
})
export class ContactusComponent {
  sendEmail() {
    window.open("mailto:info@travelplus.com?subject=Contact Us&body=Please enter your message here.");
  }
}


