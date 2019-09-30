import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = 'https://localhost:8443';
  }

  existsRatingForBy(to: string, from: string) {
    return this.http.get<any>(this.url+'/ratings/exist/for/'+to+"/from/"+from);
  }

  rateSeller(from: string, seller: string, rating: number) {
    return this.http.get<any>(this.url+'/ratings/seller/'+seller+"/from/"+from, {
      params: new HttpParams().set('rating', rating.toString())              // https://localhost:8443/users/verify?id=something
  })};

  rateBuyer(from: string, buyer: string, rating: number) {
    return this.http.get<any>(this.url+'/ratings/buyer/'+buyer+"/from/"+from, {
      params: new HttpParams().set('rating', rating.toString())              // https://localhost:8443/users/verify?id=something
  })};

}
