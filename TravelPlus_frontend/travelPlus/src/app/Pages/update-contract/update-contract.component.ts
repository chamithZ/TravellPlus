import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { contract } from '../../Models/Contract';
import { ContractService } from '../../Services/ContractService/contract.service';
import { Response } from '../../Models/Response';
import { SeasonService } from '../../Services/SeasonService/season.service';
import { OffersService } from '../../Services/OfferService/offers.service';

@Component({
  selector: 'app-update-contract',
  templateUrl: './update-contract.component.html',
  styleUrls: ['./update-contract.component.css']
})
export class UpdateContractComponent implements OnInit {
  contractForm!: FormGroup;
  contractId!: number;
  isDataUploaded = false;
  contract!: contract;

  constructor(
    private fb: FormBuilder,
    private contractService: ContractService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.contractId = params['contractId'];
      this.contractService.getContractById(this.contractId).subscribe((response: Response<contract>) => {
        this.contract = response?.content;
        console.log(this.contract);
        this.initializeForm();
      });
    });
  }

  initializeForm(): void {
    this.contractForm = this.fb.group({
      startDate: [this.contract.startDate],
      endDate: [this.contract.endDate],
      cancellationFeePercentage: [this.contract.cancellationFeePercentage],
      cancellationDeadline: [this.contract.cancellationDeadline],
      prepaymentPercentage: [this.contract.prepaymentPercentage],
      paymentDeadline: [this.contract.paymentDeadline],
      hotelId: [this.contract.hotelId]
      
    });

  }

  get f() {
    return this.contractForm.controls;
  }

 

  onSubmit() {
    const values = this.contractForm.value as contract;
    console.log(values)
    values.contractId = this.contractId;
    this.contractService.updateContract(values).subscribe((res) => {
      this.isDataUploaded = true;
    });
  }
}
