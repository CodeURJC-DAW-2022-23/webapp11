import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-recoverpassword',
  templateUrl: './recoverpassword.component.html',
  styleUrls: ['./recoverpassword.component.css']
})
export class RecoverpasswordComponent implements OnInit {

  email: string = '';
  constructor(private auth: AuthService, private route: ActivatedRoute, private router: Router, private appComponent: AppComponent) { }
  ngOnInit(): void {
    this.appComponent.showHeader = false;
    // Just update the URL
    this.route.paramMap.subscribe(params => {
    });
  }


  recover(email: string) {
    this.auth.recover(email).subscribe((response: any) => {

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
    this.router.navigate(['/code']);
  }

  protected readonly Component = Component;
}
