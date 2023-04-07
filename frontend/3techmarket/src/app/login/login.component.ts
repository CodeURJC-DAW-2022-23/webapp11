import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { Router } from '@angular/router';
import { AuthService } from "../services/auth.service";
import { AppComponent } from "../app.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email: string = '';
  password: string = '';
  showPassword: boolean = false;

  constructor(private auth: AuthService, private route: ActivatedRoute, private router: Router, private appComponent: AppComponent) { }

  ngOnInit() {
    this.appComponent.showHeader = false;
    // Just update the URL
    this.route.paramMap.subscribe(params => {
    });
  }

  login(email: string, password: string) {
    this.auth.login(email, password).subscribe((response: any) => {
      this.redirect();
    }, () => {
      this.toggleError();
    });
  }

  error: boolean = false;
  toggleError() {
    this.error = !this.error;
  }

  // Redirect to the home page
  redirect() {
    this.appComponent.showHeader = true;
    this.router.navigate(['/']);
  }

  protected readonly Component = Component;
}
