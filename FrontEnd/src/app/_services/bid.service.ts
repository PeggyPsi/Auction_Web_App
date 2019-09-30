import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Bid } from '../_models/bid/bid';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = 'https://localhost:8443';
  }
  
  // return bids
  // limit number returned if provided
  // descending 
  // maxNum=0 -> all bids of item
  // maxNum>0 -> maxNum highest bids
  getBids(itemId: number, maxNum: number) {
    return this.http.get<any>(this.url + '/bids/' + itemId, {
      params: new HttpParams().set('maxNum', maxNum.toString())
    }); 
  }

  // add new bid 
  addBid(bid: Bid, bought: boolean) {
    return this.http.post<any>(this.url + '/bids', bid, {params: new HttpParams().set('bought', bought.toString())});
  }
}
