import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

    private loginUrl = 'http://localhost:8080/login';

    constructor(private http: HttpClient) { }

    authenticateUser(username: string, password: string) {
      return this.http.post<any>(this.loginUrl, { username, password });
    }
}
