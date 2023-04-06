import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post('/api/auth/login', { username, password });
  }

  logout() {
    this.http.post('/api/auth/logout', {}).subscribe(() => {
      console.log('Logged out'); // For some reason it doesn't log out unless I subscribe and do something
    });
  }

  authdetails(): Observable<any> {
    return this.http.get('/api/user/profile');
  }
}
