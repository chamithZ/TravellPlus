import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(private router: Router) {}
  title = 'travelPlus';

  showHeaderAndFooter(): boolean {

    const routesWithoutHeaderAndFooter = ['/addHotel'];

    return !routesWithoutHeaderAndFooter.includes(this.router.url);
  }
}
