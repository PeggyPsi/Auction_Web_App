import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {

  searchForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {
    this.searchForm = this.fb.group({
      text: ""
    })
  }

  search() {
    // No text provided 
    if(this.searchForm.get("text").value === "") {
      // this.router.navigate(["/browse", "all"]);
      this.router.navigate(["/search"]);
    }
    // // Search based on text
    else {
      this.router.navigate(["/search", {search: this.searchForm.get("text").value}]);
    }
  }

  onInputChangeSearch(text: string) {
    if(this.router.url.includes("search")){
      this.search();
    }
  }

}
