import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PartnerService  {
  private apiUrl = 'http://localhost:8080/api/v1/partners'; // Thay bằng URL thật

  constructor(private http: HttpClient) {}

  getPartners(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
  importExcel(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}/import`, formData);
  }
}
