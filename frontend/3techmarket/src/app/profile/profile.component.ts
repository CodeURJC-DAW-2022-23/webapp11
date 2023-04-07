import { Component, OnInit } from '@angular/core';
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

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.authService.authdetails().subscribe((response: any) => {
      this.data = response;
      if (this.data.pfp) {
        this.haspfp = true;
      }
      // Check if response was 403 and serve the forbidden page
      if (response.status === 403) {
        this.router.navigate(['/forbidden']);
      }
    });
  }

  savepfp() {

  }

  save() {

  }




}
