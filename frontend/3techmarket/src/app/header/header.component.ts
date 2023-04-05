import { Component } from '@angular/core';

@Component({
  selector: 'header-root',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  searchTerm = '';
  showSearchResults = false;

  search() {
    this.showSearchResults = true;
  }

  protected readonly Component = Component;
}
