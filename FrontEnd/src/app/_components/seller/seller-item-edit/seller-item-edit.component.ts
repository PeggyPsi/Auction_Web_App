import { Component, OnInit, ViewChild } from '@angular/core';
import { User } from 'src/app/_models/user';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Category } from 'src/app/_models/category/category';
import { ItemService, ImageService, AlertService, AuthenticationService, CategoryService } from 'src/app/_services';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { EndStartDateValidator } from 'src/app/_custom-validators/date/end-start-date.validator';
import { Item } from 'src/app/_models/item/item';
import { Observable } from 'rxjs/internal/Observable';
import { Image } from 'src/app/_models/item/images/image';
import { MapItemCreateComponent } from '../../open-street-map/map-item-create/map-item-create.component';
import { InitialBuyPriceValidator } from 'src/app/_custom-validators/prices/initial-buy-price.validator';

@Component({
  selector: 'app-seller-item-edit',
  templateUrl: './seller-item-edit.component.html',
  styleUrls: ['./seller-item-edit.component.css']
})
export class SellerItemEditComponent implements OnInit {

  @ViewChild(MapItemCreateComponent, {static: false}) child: MapItemCreateComponent;

  currentHome: String;
  currentUser: User;
  itemForm: FormGroup;
  loading = false;
  submitted = false;
  categoriesArray: string[];
  categories: Category[];
  item: Item;
  loadedItem: boolean;
  // IMAGES UPLOAD AND DELETE
  multi_images: File[] = [];          // new images from item edit
  item_images: Image[] = [];          // images already uploaded for specific item
  html_images = [];

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
  }

  ngOnInit() {
    this.item = new Item();

    // Retrieve categories
    this.retrieveCategories();

    // Retrieve the item id parameter
    let itemId = this.route.snapshot.paramMap.get('itemId');

    // Retrieve item from database
    this.loadItemDetails(+itemId);          // + converts string to number

    // Get info about the current user
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);

    // console.log(this.item);

    // Initialize item reactive form
    this.itemForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      firstBid: ['', Validators.required],
      auctionStart: ['', Validators.required],
      auctionEnd: ['', Validators.required],
      buyOffer: [''],
      // location: this.formBuilder.group({
      //   id: [''],
      //   location: ['', Validators.required],
      //   latitude: [''],
      //   longitude: [''],
      //   country: ['', Validators.required]
      // }),
      categories: ['', Validators.required],
      id: ['']
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

  retrieveCategories() {
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
      }
    );
  }

  loadItemDetails(itemId: number) {
    // Retrieve item from database with its pictures
    this.itemService.getItem(itemId)
    .pipe(first())
    .subscribe(
      data => {
        this.item = data;
        // console.log(this.item);
        // this.itemForm.patchValue(data);
        this.updateForm();
        this.loadedItem = true;
      },
      error=> {
        // console.error(error.error.message);
        this.alertService.error(error);
      }
    );
  }

  onSelectFile(event) {
    console.log(event);
    if (event.target.files && event.target.files[0]) {

      for (let i=0 ; i<event.target.files.length ; i++) {
        let image: File = event.target.files[i];
        var reader = new FileReader();
        let large = false;
        console.log(image.size)
        if(image.size > 1048576) {
          large = true;
        }
        else {
          console.log("hello");
          this.multi_images.push(image);
        }

        reader.onload = (event:any) => {
          // this.html_images.push(event.target.result);        //event.target.result is the actual image 
          console.log("pushed");
          this.html_images.push({ url: event.target.result, image_ref: image, large: large});
        }
  
        reader.readAsDataURL(image);
      }
    }
  }

  // exists: image already uploaded -> remove image from database
  removeImageExists(imageId: number) {
    // console.log("Remove existing image");
    this.imageService.deleteImage(imageId)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.success("Image was deleted successfully.");
        for(let index=0 ; index<this.item.images.length ; index++){
          if(this.item.images[index].id === imageId){
            this.item.images.splice(index,1);
            continue;
          } 
        }
      },
      error=> {
        this.alertService.error("There was an error when deleting the specific image.");
      }
    );
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

  updateForm() {
    // Convert categories to tag-input model
    let categories = [];
    this.item.categories.forEach(category => {
      categories.push({'display': category.name, 'value': category.name});
    });

    // console.log(this.item);
    this.itemForm.patchValue({                // patch value -> set specific values not all of them
      id: this.item.id,
      name: this.item.name,
      description: this.item.description,
      firstBid: this.item.firstBid,
      auctionStart: this.item.auctionStart.toString().substr(0, 16),
      auctionEnd: this.item.auctionEnd.toString().substr(0, 16),
      categories: categories,
      buyOffer: this.item.buyOffer
    })

    // // this.itemForm.get('location').patchValue(this.item.location);
    // this.itemForm.controls['location'].setValue ({
    //   id: this.item.location.id,
    //   location: this.item.location.location,
    //   latitude: this.item.location.latitude,
    //   longitude: this.item.location.longitude,
    //   country: this.item.location.country
    // })
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
    // console.log(this.itemForm.get('categories').value);

    this.loading = true;
    // console.log("onSubmit");
    this.itemService.updateItem(this.itemForm.value, this.categories, this.item.seller)
      .pipe(first())
      .subscribe(
        data => {
          // this.alertService.success('Item was added successfully', true);
          // Upload Images
          this.uploadImages(this.multi_images, data.id);
          this.alertService.success("Item was edited successfully", true);
          this.router.navigate(['user/seller/items/id', data.id, {successEdit: 'true'}]);
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
