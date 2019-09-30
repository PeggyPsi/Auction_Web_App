import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertService, ItemService, AuthenticationService, AppService, CategoryService } from 'src/app/_services';
import { Category } from 'src/app/_models/category/category';
import { User } from 'src/app/_models/user/user';
import { EndStartDateValidator } from 'src/app/_custom-validators/date/end-start-date.validator';
import { ImageService } from 'src/app/_services/image.service';
import { Subject } from 'rxjs';
import { MapItemCreateComponent } from '../../open-street-map/map-item-create/map-item-create.component';
import { InitialBuyPriceValidator } from 'src/app/_custom-validators/prices/initial-buy-price.validator';

@Component({
  selector: 'app-item-create',
  templateUrl: './item-create.component.html',
  styleUrls: ['./item-create.component.css']
})
export class ItemCreateComponent implements OnInit {

  @ViewChild(MapItemCreateComponent, {static: false}) child: MapItemCreateComponent;

  currentDate: string;
  currentHome: String;
  currentUser: User;
  itemForm: FormGroup;
  loading = false;
  submitted = false;
  categoriesArray: string [];
  categories: Category[];
  // message: string;

  constructor(
    private itemService: ItemService,
    private imageService: ImageService,
    private categoryService: CategoryService,
    private formBuilder:FormBuilder,
    private route:ActivatedRoute,
    private router:Router, 
    private alertService: AlertService,
    private authenticationService: AuthenticationService
    // private appService: AppService
  ) {
    // get current date
    let date = new Date();
    // console.log(date.toISOString());
    this.currentDate = date.toISOString().substr(0,10) + "T" + date.toTimeString().substr(0,5);
    console.log(this.currentDate);

    this.categoryService.getCategories()
    .pipe(first())
    .subscribe(
      data => {
        this.categories = [];
        // retrieve categories from database
        this.categoriesArray = [];
        data.forEach(category => {
          // console.log(category);
          this.categoriesArray.push(category.name);
        });
      },
      error=> {
        this.alertService.error(error);
        this.loading=false;
      }
    );
  }

  ngOnInit() {
    // Inantiate message for successfull creation of item
    // this.route.queryParams
    //   .subscribe(params => {
    //     if(params.success !== undefined && params.success === 'true') {
    //       this.message = 'Item was added successfully';
    //     }
    //   });

    // Get info about the current user
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);

    // Initialize item reactive form
    this.itemForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      firstBid: ['', Validators.required],
      auctionStart: [this.currentDate, Validators.required],
      auctionEnd: [this.currentDate, Validators.required],
      buyOffer: [''],
      // location: this.formBuilder.group({
      //   location: ['', Validators.required],
      //   latitude: [''],
      //   longitude: [''],
      //   country: ['', Validators.required]
      // }),
      categories: ['', Validators.required]
      // sellerId: [this.currentUser.publicId, Validators.required]
    },
    {
      validators: [EndStartDateValidator.endStartDateValidator, InitialBuyPriceValidator.initialBuyPriceValidator]
    }
    );
  }

  get f() { return this.itemForm.controls; }
  get floc() : FormGroup{
    return this.itemForm.controls['location'] as FormGroup;
  }

  // IMAGES UPLOAD
  multi_images: File[] = [];
  html_images = [];
  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {

      for (let i=0 ; i<event.target.files.length ; i++) {
        let image: File = event.target.files[i];
        var reader = new FileReader();
        let large = false;
        // Image too large
        console.log(image.size);
        if(image.size > 1048576) {
          console.log("HEY");
          large = true;
        }
        else {
          this.multi_images.push(image);
        }

        reader.onload = (event:any) => {
          // this.html_images.push(event.target.result);        //event.target.result is the actual image 
          this.html_images.push({ url: event.target.result, image_ref: image, large: large});
        }
  
        reader.readAsDataURL(image);
      }
    }
  }

  removeImage(i: number, image: File){ 
    this.html_images.splice(i, 1); 
    // Search file array for file with specific name
    for(let index=0 ; index < this.multi_images.length ; index++ ) {
      if(this.multi_images[index].name === image.name){
        // console.log("hey");
        this.multi_images.splice(index, 1);
        continue;
      }
    }
    // console.log(this.multi_images);
  }

  onSubmit() {
    this.child.submitted = true;
    this.submitted = true;

    console.log(this.itemForm.controls);

    // stop here if form is invalid
    if (this.itemForm.invalid) {
        // console.log("inavlid");
        return;
    }

    // convert tags input to array of categories
    this.itemForm.get('categories').value.forEach(category => {
      this.categories.push(new Category(category.value));
    }); 
    // this.itemForm.get('categories').setValue(this.categories);
    // console.log(this.itemForm.get('categories'));
    // console.log("hey");
    // console.log(this.itemForm.value);
    // console.log(this.categories);

    // console.log("onSubmit");
    this.itemService.addItem(this.itemForm.value, this.categories, this.currentUser)
      .pipe(first())
      .subscribe(
        data => {
          // this.alertService.success('Item was added successfully', true);
          // Upload Images
          this.uploadImages(this.multi_images, data.id);
          // localStorage.setItem("message", "Item was added to your auction collection successfully");
          // this.appService.refreshPage('user/seller/sellitem', null);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }

  uploadImages(images: File[], itemId: number) {
    if (images.length > 0){
      for(let i = 0; i < images.length; i++) {
        // get image and send to save
        let image = images[i];
        this.imageService.uploadImage(image, itemId)
        .pipe(first())
        .subscribe(
            data => {
              // this.alertService.success('Image was added successfully');
              if(i==images.length-1) {
                this.alertService.success("Item was created successfully", true);
                this.router.navigate(['user/seller/items/id', itemId, {successAdd: 'true'}]);
              }
            },
            error => {
              this.alertService.error(error);
              this.loading = false;
        });
      }
    }
    return;
  }
}
