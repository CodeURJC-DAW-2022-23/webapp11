import {Component, OnInit} from '@angular/core';
import { MessagesService } from '../services/messages.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {
  messages: any = [];
  messageText: any;
  id: string = this.route.snapshot.paramMap.get('id') || '';  // Get the id from the url, no need to subscribe to the route on the ngOnInit, magic
  // snapshot is an image of the route information created after the component was created, paramMap is a dictionary of route parameter values extracted from the URL
  // We are looking for the id, which was passed in the url, we know is called id because we defined it in the app-routing.module.ts file
  isUser: boolean = false;

  constructor(private authService: AuthService, private router: Router, private messageService: MessagesService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.authService.authdetails().pipe(
      catchError((error) => {
        this.router.navigate(['/forbidden']);
        return throwError(error);
      })
    ).subscribe((data) => {
      // Check if the user is an agent
      if (data.roles.includes('AGENT')) {
        this.getConversation(this.id);
      } else {
        this.isUser = true;
        this.getUserMessages();
      }
    });
  }

  getUserMessages() {
    this.messageService.getUserMessages().subscribe((data) => {
      this.messages = data;
    });
  }

  sendMessageUser() {
      this.messageService.sendMessageUser(this.messageText).subscribe((data) => {
      this.getUserMessages();
    });
    this.messageText = '';
  }

  getConversation(id: string) {
    this.messageService.getConversation(id).subscribe((data) => {
      this.messages = data;
    });
  }

  sendMessageAgent() {
    this.messageService.sendMessageAgent(this.messageText, this.id).subscribe((data) => {
      this.getConversation(this.id);
    });
    this.messageText = '';
  }
}
