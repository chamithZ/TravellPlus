import { Component, OnInit } from '@angular/core';
import { SupplementService } from '../../Services/SupplementService/supplement.service';
import { ActivatedRoute } from '@angular/router';
import { ContractService } from '../../Services/ContractService/contract.service';

@Component({
  selector: 'app-supplement-list',
  templateUrl: './supplement-list.component.html',
  styleUrls: ['./supplement-list.component.css']
})
export class SupplementListComponent implements OnInit {
  supplements: any[] = [];
  contractId!: number;
  loadingSupplements: boolean = true;
  hotelId!: number;

  constructor(
    private supplementService: SupplementService,
    private route: ActivatedRoute,
    private contractService:ContractService
  ) {}

  ngOnInit() {
    // Assuming you retrieve contractId from route parameters
    this.hotelId = parseInt(this.route.snapshot.paramMap.get('hotelId') || '');
    const today = new Date().toISOString().slice(0, 10);
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const tomorrowStr = tomorrow.toISOString().slice(0, 10);

    this.contractService.getContractIdByHotelIdAndDateRange(this.hotelId, today, tomorrowStr).subscribe(contract => {
      this.supplementService.getSupplementList(contract.content).subscribe((response) => {
        if (response && Array.isArray(response)) {
        
          response.forEach(supplement => {
            // Here, you can perform any operations you want on each roomType
            // For example, you can push each roomType to the roomTypes array
            this.supplements.push(supplement);
          });
        
      }
        this.loadingSupplements = false;
      });

    });
    
   
  }
}
