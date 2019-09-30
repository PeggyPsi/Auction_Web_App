import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  url: string;

  constructor(private http: HttpClient) {
    this.url = 'https://localhost:8443';
  }

  uploadImage(image: File, itemId: number){
    let formdata: FormData = new FormData();
    formdata.append('image', image);

    let curUrl = this.url+'/items/'+itemId+'/images';
    return this.http.post<any>(curUrl, formdata);
  }

  deleteImage(imageId: number) {
    let curUrl = this.url+'/items/images/'+imageId;
    return this.http.delete<any>(curUrl);
  }
}
