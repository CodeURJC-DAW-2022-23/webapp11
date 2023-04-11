import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  email: string = '';
  password: string = '';
  firstName: string = '';
  lastName: string = '';
  showPassword: boolean = false;

  constructor(private auth: AuthService, private route: ActivatedRoute, private router: Router, private appComponent: AppComponent) { }


  register(email: string, password: string, firstName: string, lastName: string) {
    this.auth.register(email, password, firstName, lastName).subscribe((response: any) => {
      this.redirect();
    }, () => {
      this.toggleError();
    });
  }

  error: boolean = false;
  toggleError() {
    this.error = !this.error;
  }


  ngOnInit(): void {
    this.appComponent.showHeader = false;
    // Just update the URL
    this.route.paramMap.subscribe(params => {
    });
  }

  redirect() {
    this.appComponent.showHeader = true;
    this.router.navigate(['/']);
  }

}
