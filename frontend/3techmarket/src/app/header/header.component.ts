import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'header-root',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  searchTerm = '';
  showSearchResults = false;

  constructor(private router: Router) {}

  search() {
    this.router.navigate(['/search', this.searchTerm]);
  }

  login() {
    this.router.navigate(['/login']);
  }

  protected readonly Component = Component;
}
