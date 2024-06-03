import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators, AbstractControl } from '@angular/forms';
import { contract } from '../../Models/Contract';
import { ContractService } from '../../Services/ContractService/contract.service';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2'; // Import SweetAlert

@Component({
  selector: 'app-add-contract',
  templateUrl: './add-contract.component.html',
  styleUrls: ['./add-contract.component.css']
})
export class AddContractComponent implements OnInit {
  contractForm!: FormGroup;
  hotelId!: number;
  currentDate: string = new Date().toISOString().split('T')[0];
  tomorrowDate: string = new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toISOString().split('T')[0];
  maxDate: string = new Date(new Date().getFullYear() + 5, new Date().getMonth(), new Date().getDate()).toISOString().split('T')[0];
  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private contractService: ContractService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.hotelId = params['hotelId'];
    });

    this.contractForm = this.formBuilder.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      cancellationFeePercentage: ['', [Validators.required, this.percentageValidator()]],
      cancellationDeadline: ['', Validators.required],
      prepaymentPercentage: ['',[Validators.required, this.percentageValidator()]],
      paymentDeadline: ['', Validators.required],
      hotelId: [this.hotelId, Validators.required],
      season: this.formBuilder.array([]), 
      offer: this.formBuilder.array([])
    });
  }

  get season(): FormArray {
    return this.contractForm.get('season') as FormArray;
  }

  addSeason(): void {
    const newSeasonGroup = this.formBuilder.group({
      seasonType: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      markUp: ['', Validators.required]
    });



    this.season.push(newSeasonGroup);
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
      discountPercentage: ['', [Validators.required, this.percentageValidator()]]
    }));
  }

  removeOffer(index: number): void {
    this.offer.removeAt(index);
  }
  get f() {
    return this.contractForm.controls;
  }
  validateCancellationFeePercentage(): void {
    const cancellationFeePercentageControl = this.contractForm.get('cancellationFeePercentage');
    if (cancellationFeePercentageControl) {
      cancellationFeePercentageControl.updateValueAndValidity();
    }
  }

  

  onSubmit(): void {
    if (this.contractForm.invalid) {
      // Form is invalid, show error alert
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Please fill out all required fields correctly!',
      });
      return;
    }

    const values = this.contractForm.value as contract;
    console.log(values);

    this.contractService.addContract(values as contract).subscribe((res) => {
      console.log('Contract added successfully:', res);
      // Show success alert
      Swal.fire({
        icon: 'success',
        title: 'Success',
        text: 'Contract added successfully!',
        confirmButtonText: 'OK'
      }).then((result) => {
        if (result.isConfirmed) {
          this.contractForm.reset();
          this.router.navigate(['/addRoomType', res.content.contractId]);
        }
      });
    });
  }
 
  percentageValidator() {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const value = control.value;
      if (isNaN(value) || value < 1 || value > 100) {
        return { 'invalidPercentage': true };
      }
      return null;
    };
  }
  validateDiscountPercentage(i: number): void {
    const discountPercentageControl = this.contractForm.get(`offer.${i}.discountPercentage`);
    if (discountPercentageControl) {
      discountPercentageControl.updateValueAndValidity();
    }
  }
}
