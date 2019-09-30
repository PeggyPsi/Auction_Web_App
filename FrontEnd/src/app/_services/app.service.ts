import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(
    private router: Router
  ) { }


  //Refresh page
  refreshPage(page: Array<any>) {
    this.router.navigateByUrl("/refresh", {skipLocationChange: true}).then(() => {
      this.router.navigate(page);
    });
  }
}
