import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Item } from '../_models/item/item';
import { Category } from '../_models/category/category';
import { Image } from '../_models/item/images/image';
import { User } from '../_models/user/user';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = 'https://localhost:8443';
  }

  searchByText(text: string) {
    return this.http.get<any>(this.url+'/items/search', {params: new HttpParams().set('text', text)});
  }

  extractToJson(itemId: number) {
    return this.http.get<any>(this.url+'/items/toJSON/'+itemId);
  }

  extractToXml(itemId: number) {
    return this.http.get<any>(this.url+'/items/toXML/'+itemId);
  }

  addItem(item: Item, categories: Category[], seller: User) {
    item.categories = categories;
    item.seller = seller;
    console.log(item);
    return this.http.post<Item>(this.url+'/items', item);
  }

  updateItem(item: Item, categories: Category[], seller: User) {
    item.categories = categories;
    item.seller = seller;
    console.log(item);
    return this.http.put<Item>(this.url+'/items/update', item);
  }

  getItem(itemId: number) {
    return this.http.get<Item>(this.url + '/items/' + itemId);
  }

  deleteItem(itemId: number) {
    return this.http.delete<any>(this.url + '/items/' + itemId);
  }

  getAllItems(params: any) {
    return this.http.get<any>(this.url + '/items/getAll', {params: params});
  }

  getItemsByNewest() {
    return this.http.get<any>(this.url + '/items/newest');
  }

  getItemsBySellerId(sellerId: string, params: any) {
    console.log(params);
    return this.http.get<any>(this.url + '/items/seller/' + sellerId, {params: params});
  }

  // getItemsByCategoryAndSellerId(categoryId: number, sellerId: string) {
  //   console.log("calling this")
  //   return this.http.get<any>(this.url + '/items/seller/' + sellerId, {params: {categoryId: '1'}});
  // }

  // getItemsByCategory(categoryId: number) {
  //   return this.http.get<any>(this.url + '/items/category/' + categoryId);
  // }

  startAuction(itemId: number) {
    return this.http.get<any>(this.url + '/items/' + itemId + "/start");
  }

  // BIDDER
  getBiddedItems(bidderId: string, params: any){
    return this.http.get<any>(this.url + '/items/bids/by/' + bidderId, {params: params})
    // query parameters have been provided
    // if(query.length > 0) {
    //   return this.http.get<any>(this.url + '/items/bids/by/' + bidderId, {
    //     params: new HttpParams().set(query[0], query[1])
    //   });
    // }
    // else {
    //   return this.http.get<any>(this.url + '/items/bids/by/' + bidderId);
    // }
  }

  getWonItems(bidderId: string, params: any){
    return this.http.get<any>(this.url + '/items/bids/won/by/' + bidderId, {params: params}) 
    // query parameters have been provided
    // if(query.length > 0) {
    //   return this.http.get<any>(this.url + '/items/bids/won/by/' + bidderId, {
    //     params: new HttpParams().set(query[0], query[1])
    //   });
    // }
    // else {
    //   return this.http.get<any>(this.url + '/items/bids/won/by/' + bidderId);
    // }
  }
}
