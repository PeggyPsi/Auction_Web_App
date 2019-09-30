import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Category } from 'src/app/_models/category/category';
import { CategoryService, AlertService, ItemService } from 'src/app/_services';
import { first } from 'rxjs/internal/operators/first';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  @Output() onCategoryPicked: EventEmitter<any> = new EventEmitter<any>();

  categories: Category[];
  loadCat = false; 
  currentCategory; 

  constructor(
    private categoryService: CategoryService,
    private alertService: AlertService,
    private router: Router
  ) { }

  ngOnInit() {
    this.categories = [];
    this.loadCategories();
    this.currentCategory = "None";
    // form user items to browse or seller items by clicking to category
    if(this.router.url.includes('name')) {
      this.currentCategory = this.router.url.split(';')[2].split('=')[1];
    }
  }

  loadCategories(){
    this.categoryService.getCategories()
    .pipe(first())
    .subscribe(
      data => {
        this.categories = data;
        this.loadCat = true;
      },
      error => {
        this.alertService.error(error);
      });
  }

  // User selected specific category of items
  onSelectCategory(category: Category) {
    this.currentCategory = category.name;
    let url = this.router.url.split(';')[0];
    // if(url.includes('user/items')) this.router.navigate(["/browse", "all", {category: category.id, name: category.name}]);
    // // else if(url.includes('browse'))
    // else this.router.navigate([url, {category: category.id, name: category.name}]);
    this.onCategoryPicked.emit({category: category.id, name: category.name});
  }

  onSelectNoCategory() {
    this.currentCategory = "None";
    // let url = this.router.url.split(';')[0];
    // if(url.includes('user/items')) this.router.navigate(["/browse", "all"]);
    // // else if(url.includes('browse'))
    // else this.router.navigate([url]);   
    this.onCategoryPicked.emit({});
  }

}
