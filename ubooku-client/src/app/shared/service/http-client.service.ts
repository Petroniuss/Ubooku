import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ACCESS_TOKEN } from '../urls';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  constructor(private http: HttpClient) { }

  
  get(url: string, params?: HttpParams) {
    let headers = new HttpHeaders();
    this.appendAuthorizationHeader(headers);
    return this.http.get(url, {
      headers: headers,
      params: params,
    });
  }

  getImg(url: string) {
    let headers = new HttpHeaders();
    this.appendAuthorizationHeader(headers);
    return this.http.get(url, {
      responseType: "blob"
    })
  }

  post(url: string, body: any) {
    let headers = new HttpHeaders();
    this.appendAuthorizationHeader(headers);
    return this.http.post(url, body, {
      headers: headers
    })
  }

  appendAuthorizationHeader(headers: HttpHeaders) {
    var token = localStorage.getItem(ACCESS_TOKEN);
    if(token) {
      headers.append('Authorization', token);
    }
  }


}
