import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { contract } from '../../Models/Contract';
import { ContractService } from '../../Services/ContractService/contract.service';

@Component({
  selector: 'app-add-contract',
  templateUrl: './add-contract.component.html',
  styleUrls: ['./add-contract.component.css']
})
export class AddContractComponent implements OnInit {
  contractForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder, 
    private contractService: ContractService
    ) {}

  ngOnInit(): void {
    this.contractForm = this.formBuilder.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      cancellationFeePercentage: ['', Validators.required],
      cancellationDeadline: ['', Validators.required],
      prepaymentPercentage: ['', Validators.required],
      paymentDeadline: ['', Validators.required],
      hotelId:1,
      season: this.formBuilder.array([]),
      offer: this.formBuilder.array([])
    });
  }

  get season(): FormArray {
    return this.contractForm.get('season') as FormArray;
  }

  addSeason(): void {
    this.season.push(this.formBuilder.group({
      seasonType: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      markUp: ['', Validators.required]
    }));
  }

  removeSeason(index: number): void {
    this.season.removeAt(index);
  }

  get offer(): FormArray {
    return this.contractForm.get('offer') as FormArray;
  }

  addOffer(): void {
    this.offer.push(this.formBuilder.group({
      offerName: ['', Validators.required],
      offerType: ['', Validators.required],
      conditions: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      discountPercentage: ['', Validators.required]
    }));
  }

  removeOffer(index: number): void {
    this.offer.removeAt(index);
  }

  onSubmit(): void {
 
      const values=this.contractForm.value as contract;
      console.log(values);
      this.contractService.addContract(values as contract).subscribe((res)=>{
      this.contractForm.reset();
      })
 
  }
}



