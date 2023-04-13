import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-code',
  templateUrl: './code.component.html',
  styleUrls: ['./code.component.css']
})
export class CodeComponent implements OnInit {

  email: string = '';
  password: string = '';

  code: string = "";
  showPassword: boolean = false;

  constructor(private auth: AuthService, private route: ActivatedRoute, private router: Router, private appComponent: AppComponent) { }


  ngOnInit() {
    this.appComponent.showHeader = false;
    // Just update the URL
    this.route.paramMap.subscribe(params => {
    });
  }

  verify(email: string, code: string, password: string) {
    this.auth.verify(email, code, password, "", "").subscribe((response: any) => {

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
