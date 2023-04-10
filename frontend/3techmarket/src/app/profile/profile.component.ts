import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  @ViewChild('pfp') pfpRef: ElementRef | undefined;
  loading: boolean = true;
  haspfp: boolean = false;
  data: any;
  firstName: string = "";
  lastName: string = "";
  phoneNumber: string = "";
  address: string = "";
  postcode: string = "";
  statee: string = "";
  country: string = "";
  city: string = "";
  area: string = "";

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((response: any) => {
      this.loading = false;
      this.data = response;
      this.haspfp = !!response.profilePicture;
    });
  }

  savepfp() {
    this.loading = true;
    // Save the state of the haspfp variable
    const haspfp = this.haspfp;
    this.haspfp = false;
    const file = (this.pfpRef?.nativeElement as HTMLInputElement).files?.[0]; // The ? is to check if it's undefined, as we handle that in the if statement
    if (file) {
      this.authService.updatePfp(file).subscribe((response: any) => {
        this.ngOnInit();
      });
    } else {
      this.haspfp = haspfp;
      this.loading = false;
    }
  }

  deletepfp() {
    this.authService.deletepfp().subscribe((response: any) => {
      console.log(response);
    });
    this.haspfp = false;
  }

  save(firstName: string, lastName: string, phoneNumber: string, address: string, postcode: string, statee: string, country: string, city: string, area: string) {
    // If the value is empty, send the old value because it is a put request, and it will overwrite the value with an empty string otherwise
    if (firstName === "") {
      firstName = this.data.firstName;
    }
    if (lastName === "") {
      lastName = this.data.lastName;
    }
    if (phoneNumber === "") {
      phoneNumber = this.data.phoneNumber;
    }
    if (address === "") {
      address = this.data.address;
    }
    if (postcode === "") {
      postcode = this.data.postcode;
    }
    if (statee === "") {
      statee = this.data.state;
    }
    if (country === "") {
      country = this.data.country;
    }
    if (city === "") {
      city = this.data.city;
    }
    if (area === "") {
      area = this.data.area;
    }

    this.authService.updateProfile(firstName, lastName, phoneNumber, address, postcode, statee, country, city, area).subscribe((response: any) => {
      this.ngOnInit();
      this.authService.savedProfile();
    });
  }
}

