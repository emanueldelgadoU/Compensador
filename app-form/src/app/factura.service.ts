import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  public apiUrlPost = '/envioformulario';
  public baseUrl = 'http://localhost:8080/api';

  constructor(public http: HttpClient) {}

  enviarFormulario(jsonEnvioDePrueba: any): Observable<any> {
    const url = this.baseUrl + this.apiUrlPost;
    return this.http.post(url, jsonEnvioDePrueba);
  }

  getFacturas(userId: number): Observable<any> {
    const url = `${this.baseUrl}/facturascompensar?userId=${userId}`;
    return this.http.get(url);
  }
}
