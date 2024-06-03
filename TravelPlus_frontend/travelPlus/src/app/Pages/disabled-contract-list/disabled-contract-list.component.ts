import { Component, OnInit } from '@angular/core';
import { ContractService } from '../../Services/ContractService/contract.service';
import { contract } from '../../Models/Contract';
import { Response } from '../../Models/Response';
import { HotelService } from '../../Services/HotelService/hotel.service';

@Component({
  selector: 'app-disabled-contract-list',
  templateUrl: './disabled-contract-list.component.html',
  styleUrl: './disabled-contract-list.component.css'
})
export class DisabledContractListComponent {

  contracts: any[] = []; // Placeholder for contracts
  currentPage = 0;
  pageSize = 10; // Set your desired page size
  totalPages = 0;

  constructor(private contractService:ContractService,private hotelService:HotelService) { }

  ngOnInit(): void {
    // Load contracts
    this.loadContracts();
  }
  loadContracts(): void {
    this.contractService.getAllDisabledContracts(this.currentPage, this.pageSize,"disabled")
      .subscribe((response: Response<contract[]>) => {
        this.contracts = response?.content;
        console.log('contract:', this.contracts);
        this.totalPages = Math.ceil(response.totalCount / this.pageSize);
  
        // Fetch hotel details for each contract
        this.contracts.forEach(contract => {
          this.hotelService.getHotelById(contract.hotelId).subscribe(hotel => {
            contract.hotel = hotel.content;
          });
        });
      });
  }

  deleteContract(contractId: number): void {
    this.contractService.enableContract(contractId).subscribe((response: Response<any>) => {
      if (response.message == "success") {
        this.contracts = this.contracts.filter( contract => contract.contractId !== contractId);
      } else {
        // Handle error or display error message
      }
    });
  }
  

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadContracts();
  }
  getPageNumbers(): number[] {
    const pages = [];
    for (let i = 1; i <= this.totalPages; i++) {
      pages.push(i);
    }
    return pages;
  }

}
