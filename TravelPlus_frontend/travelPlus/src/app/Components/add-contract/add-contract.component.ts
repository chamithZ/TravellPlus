import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { contract } from '../../Models/Contract';
import { ContractService } from '../../Services/contract.service';

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
      seasons: this.formBuilder.array([]),
      offers: this.formBuilder.array([]),
      hotelId: 1
    });
  }

  get seasons(): FormArray {
    return this.contractForm.get('seasons') as FormArray;
  }

  addSeason(): void {
    this.seasons.push(this.formBuilder.group({
      seasonType: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      markUp: ['', Validators.required]
    }));
  }

  removeSeason(index: number): void {
    this.seasons.removeAt(index);
  }

  get offers(): FormArray {
    return this.contractForm.get('offers') as FormArray;
  }

  addOffer(): void {
    this.offers.push(this.formBuilder.group({
      offerName: ['', Validators.required],
      offerType: ['', Validators.required],
      conditions: ['', Validators.required],
      discountPercentage: ['', Validators.required]
    }));
  }

  removeOffer(index: number): void {
    this.offers.removeAt(index);
  }

  onSubmit(): void {
 
      const values=this.contractForm.value as contract;
      this.contractService.addContract(values as contract).subscribe((res)=>{
        this.contractForm.reset()
      console.log(this.contractForm.value);
      this.contractForm.reset();
      })
 
  }
}



