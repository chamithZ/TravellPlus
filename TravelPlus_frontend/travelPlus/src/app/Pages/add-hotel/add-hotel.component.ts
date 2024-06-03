import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HotelService } from '../../Services/HotelService/hotel.service';
import { Hotel } from '../../Models/Hotel';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-hotel',
  templateUrl: './add-hotel.component.html',
  styleUrl: './add-hotel.component.css'
})
export class AddHotelComponent implements OnInit{
  private hotelId!: number;

  hotelForm=this.fb.group({
    hotelName: ['', Validators.required],
    hotelAddress: ['', Validators.required],
    hotelCity: ['', Validators.required],
    hotelEmail: ['', [Validators.required, Validators.email]],
    hotelContactNo: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]], 
    hotelDescription: ['', Validators.required],
    hotelImages: this.fb.array([])
  })

  isDataUploaded=false;

  constructor(
    private fb:FormBuilder,
    private hotelService:HotelService,
    private router:Router
  ){}

  ngOnInit(): void { }

  get f(){
    return this.hotelForm.controls;
  }
  onFileChange(event: any) {
    const files = event.target.files;
    if (files) {
      for (const file of files) {
        this.convertFileToBase64(file)
          .then((base64Data: string) => {
            if (this.hotelForm.value.hotelImages) {
              this.hotelForm.value.hotelImages.push({
                imageName: file.name,
                imagePath: base64Data
              });
            }
          })
          .catch((error: any) => {
            console.error('Error converting file to base64:', error);
          });
      }
    }
  }
  
  convertFileToBase64(file: File): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = (event: any) => {
        const base64Data = btoa(event.target.result);
        resolve('data:' + file.type + ';base64,' + base64Data);
      };
      reader.onerror = (error) => {
        reject(error);
      };
      reader.readAsBinaryString(file);
    });
  }
  
  

  

 
OnSubmit() {
  const values = this.hotelForm.value as Hotel;
  this.hotelService.addHotelComponent(values).subscribe((res: any) => {
    this.isDataUploaded = true;
    this.hotelForm.reset();
    console.log(this.hotelId)
    this.hotelId=res.content;
    this.router.navigate(['/addContract', this.hotelId]);
  
  });
  
}

}


