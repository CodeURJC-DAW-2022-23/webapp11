import {Component, OnInit} from '@angular/core';
import { MessagesService } from '../services/messages.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-chats',
  templateUrl: './chats.component.html',
  styleUrls: ['./chats.component.css']
})
export class ChatsComponent implements OnInit {
  chats: boolean = false;
  users: any;
  loading: boolean = true;

  constructor(private authService: AuthService, private router: Router, private messageService: MessagesService) { }

  ngOnInit() {
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((data) => {
      // Check if the user is an agent
      if (data.roles.includes('AGENT')) {
        this.getChats();
      } else {
        this.router.navigate(['/forbidden']);
      }
    });
  }

  private getChats() {
    this.messageService.getAssistanceUsers().subscribe((data: any) => {
      this.chats = true;
      this.users = [];
      data.forEach((user: any) => {
        this.authService.getUserById(user).subscribe((data: any) => {
          this.users.push(data);  // Push the user details to the users array
        });
      });
      this.loading = false;
    });
  }

  protected readonly environment = environment;
}
