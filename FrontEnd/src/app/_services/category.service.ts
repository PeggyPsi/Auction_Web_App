import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../_models/category/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  url: string;

  constructor(private http: HttpClient) { 
    this.url = 'https://localhost:8443';
  }

  getCategories() {
    return this.http.get<Category[]>(this.url + '/categories');
  }
}
