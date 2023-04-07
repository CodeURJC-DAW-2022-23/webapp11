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

  updateProfile(firstName: string, lastName: string, phoneNumber: string, address: string, postcode: string, state: string, country: string, city: string, area: string): Observable<any> {
    return this.http.put('/api/user/profile', { firstName, lastName, phoneNumber, address, postcode, state, country, city, area });
  }

  updatePfp(file: File): Observable<any> {  // Observable is used here because we are sending a file, and we need to subscribe to it
    const formData = new FormData();  // FormData is used to send files
    formData.append('image', file);  // The name of the field is 'image' and we append the file to it, we will send this to the API
    return this.http.post('/api/images/profile-picture', formData);
  }

  deletepfp(): Observable<any> {
    return this.http.delete('/api/images/profile-picture');
  }
}
