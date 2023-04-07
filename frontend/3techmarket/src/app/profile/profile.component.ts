import { Component } from '@angular/core';
import { AuthService } from "../services/auth.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent{
  haspfp: boolean = false;
  data: any;

  constructor(private authService: AuthService, private router: Router) {
  this.authService.authdetails().subscribe((response: any) => {
      this.data = response;
      if (this.data.profilePicture) {  // If profilePicture is not null
        this.haspfp = true;
      }
      // If the response is 403, the user is not logged in, we serve the forbidden page
      if (response.status == 403) {
        this.router.navigate(['forbidden']);
      }
    });
  }

  savepfp() {

  }

  save() {

  }




}
