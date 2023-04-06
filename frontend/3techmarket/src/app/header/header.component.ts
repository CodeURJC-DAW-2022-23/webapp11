import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'header-root',
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {
  @Input() isLoggedIn: boolean = false;
  searchTerm = '';
  anonymous: boolean = false;
  admin: boolean = false;
  agent: boolean = false;
  user: boolean = false;
  authenticating: boolean = true;
  username: string = '';

  constructor(private router: Router, private authService: AuthService) {}

  search() {
    this.router.navigate(['/search', this.searchTerm]);
  }

  login() {

    this.router.navigate(['/login']);
  }

  ngOnInit() {
    this.getUserProfile();
  }

  getUserProfile() {
    this.authenticating = true;
    this.anonymous = false;
    this.authService.authdetails().subscribe((response: any) => {
      this.authenticating = false;
      this.anonymous = false;
      this.username = response.firstName;
      if (response.role === 'admin') {
        this.admin = true;
      } else if (response.role === 'agent') {
        this.agent = true;
      } else {
        this.user = true;
      }
    } , () => {
      this.anonymous = true;
      this.authenticating = false;
      }
    );
  }

  logout() {
    this.authenticating = true;
    this.authService.logout();
    this.authenticating = false;
    this.anonymous = true;
    this.admin = false;
    this.agent = false;
    this.user = false;
    this.router.navigate(['/']);
  }
}
