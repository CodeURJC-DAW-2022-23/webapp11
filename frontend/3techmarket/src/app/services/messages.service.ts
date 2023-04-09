import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class MessagesService {
  private baseUrl = '/api/user';

  constructor(private http: HttpClient, private authService: AuthService) { }

  getUserMessages() {
    const url = `${this.baseUrl}/messages`;
    return this.http.get(url);
  }

  sendMessageUser(messageText: string) {
    const url = `${this.baseUrl}/send-message`;
    return this.http.post(url, messageText);
  }

  getConversation(id: string) {
    const url = `${this.baseUrl}/messages/agent/${id}`;
    return this.http.get(url);
  }

  getAssistanceUsers() {
    // Check what users have sent messages to the agents
    const url = `/api/user/hassentmessage`;
    // This returns a list of user ids that have sent messages to the agent, we pass it to the auth service to get the user details
    return this.http.get(url);
  }

  sendMessageAgent(messageText: any, id: string) {
    const url = `/api/user/send-message/agent/${id}`;
    // Send the message raw text, without the JSON wrapper
    return this.http.post(url, messageText);
  }
}
