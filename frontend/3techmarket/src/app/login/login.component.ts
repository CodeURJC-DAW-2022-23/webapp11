import { Component, OnInit } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email: string = '';
  password: string = '';
  showPassword: boolean = false;

  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    // Just update the URL
    this.route.paramMap.subscribe(params => {
    });
  }
  login(email: string, password: string) {
    this.http.post('/api/auth/login', {
      username: email,
      password: password
    }) // The tokens are saved using cookies
    .subscribe((response: any) => {
      this.redirect();
    }, error => {
      this.toggleError();
    });
  }

  error: boolean = false;
  toggleError() {
    this.error = !this.error;
  }

  // Redirect to the home page
  redirect() {
    this.router.navigate(['/']);
  }

  protected readonly Component = Component;
}
