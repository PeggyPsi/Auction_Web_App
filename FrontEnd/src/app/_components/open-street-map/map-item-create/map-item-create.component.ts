import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormGroupDirective, ControlContainer, Validators, FormBuilder } from '@angular/forms';
import { Subject } from 'rxjs';
import { Item } from 'src/app/_models/item/item';

declare var ol: any;

// Elements that make up the popup.
var container;
var content;
var popup;
var longitude;
var latitude;
var markerLayer;
var source;
var childItemForm: FormGroup;

@Component({
  selector: 'app-map-item-create',
  templateUrl: './map-item-create.component.html',
  styleUrls: ['./map-item-create.component.css'],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }]
})
export class MapItemCreateComponent implements OnInit {

  @Input() item: Item;
  submitted: boolean;

  map: any;

  constructor(
    private parentItemForm: FormGroupDirective,
    private fb: FormBuilder,
  ) { }

  ngOnInit() {
    // Map info
    childItemForm = this.parentItemForm.form;
    childItemForm.addControl('location', this.fb.group({
      id: [''],
      location: ['', Validators.required],
      latitude: [''],
      longitude: [''],
      country: ['', Validators.required]
    }))
    // If item was provided we are in edit form
    if(this.item) {
      console.log("item provided");
      console.log(this.item);
      longitude = this.item.location.longitude;
      latitude = this.item.location.latitude;
      childItemForm.controls['location'].setValue ({
        id: this.item.location.id,
        location: this.item.location.location,
        latitude: this.item.location.latitude,
        longitude: this.item.location.longitude,
        country: this.item.location.country
      })
    }
    // We are in create form
    else {
      // Default athens
      longitude = 23.727539;
      latitude = 37.983810;
    }

    ///////////////////////////
    container = document.getElementById('popup');
    content = document.getElementById('popup-content');
    console.log(content);
    this.setPopup();
    this.setMarkerLayer();
    this.loadMap();
    this.setClickEventHandler();
  }

  get floc() : FormGroup{
    // console.log(childItemForm.controls['location']);
    return childItemForm.controls['location'] as FormGroup;
  }

  setClickEventHandler() {
    //On click
    this.map.on('singleclick', function(evt) {

      var coordinate = evt.coordinate;
      var lonlat = ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326');
      
      longitude = lonlat[0];
      latitude = lonlat[1];

      var updateFormCoordinates = function() {
        childItemForm.controls['location'].patchValue ({
          longitude: longitude,
          latitude: latitude
        })
      }

      updateFormCoordinates();
    
      // update pop up
      content.innerHTML = '<code>' + longitude + " " + latitude + '</code>';
      popup.setPosition(coordinate);
    
      // update marker
      source.clear();
    
      var marker = new ol.Feature({
        geometry: new ol.geom.Point(coordinate)
      })
    
      marker.setStyle(new ol.style.Style({
        image: new ol.style.Icon(({
            // crossOrigin: 'anonymous',
            anchor: [0.5, 48],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            src: 'https://img.icons8.com/color/48/000000/marker.png'
        }))
      }));
    
      source.addFeature(marker);

      markerLayer.setVisible(true);
    })
  }

  cancelCoordinates() {
    childItemForm.controls['location'].patchValue ({
      longitude: "",
      latitude: ""
    })
    // console.log(childItemForm.controls['location']);  
    markerLayer.setVisible(false);
    popup.setPosition(undefined);
  }

  loadMap() {

    var attribution = new ol.control.Attribution({
      collapsible: false
    });

    this.map = new ol.Map({
      controls: ol.control.defaults({attribution: false}).extend([attribution]),
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM({
              attributions: [ ol.source.OSM.ATTRIBUTION, 'Tiles courtesy of <a href="https://geo6.be/">GEO-6</a>' ],
              maxZoom: 18
          })
        }),
        markerLayer
      ],
      overlays: [popup],
      target: 'map',
      view: new ol.View({
          center: ol.proj.fromLonLat([longitude, latitude]),
          maxZoom: 18,
          zoom: 12
      })
    });

  }

  setMarkerLayer(){
    var marker = new ol.Feature({
      geometry: new ol.geom.Point(ol.proj.fromLonLat([longitude, latitude]))
    })

    marker.setStyle(new ol.style.Style({
      image: new ol.style.Icon(({
          // crossOrigin: 'anonymous',
          anchor: [0.5, 48],
          anchorXUnits: 'fraction',
          anchorYUnits: 'pixels',
          src: 'https://img.icons8.com/color/48/000000/marker.png'
      }))
    }));

    source = new ol.source.Vector();

    markerLayer = new ol.layer.Vector({
      source: source
    });

    source.addFeature(marker);
  }

  setPopup() {

    // Create an overlay to anchor the popup to the map.
    popup = new ol.Overlay({
      element: container,
      autoPan: true,
      autoPanAnimation: {
        duration: 250
      },
      position: ol.proj.fromLonLat([longitude, latitude])
    });

    content.innerHTML = '<code>' + longitude + " " + latitude + '</code>';
  }

}


// this.map.on('singleclick', function(evt) {
//   var coordinate = evt.coordinate;
//   var lonlat = ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326');
  
//   longitude = lonlat[0];
//   latitude = lonlat[1];

//   // update pop up
//   content.innerHTML = '<code>' + longitude + " " + latitude + '</code>';
//   popup.setPosition(coordinate);

//   // update marker
//   source.clear();

//   var marker = new ol.Feature({
//     geometry: new ol.geom.Point(coordinate)
//   })

//   marker.setStyle(new ol.style.Style({
//     image: new ol.style.Icon(({
//         // crossOrigin: 'anonymous',
//         anchor: [0.5, 48],
//         anchorXUnits: 'fraction',
//         anchorYUnits: 'pixels',
//         src: 'https://img.icons8.com/color/48/000000/marker.png'
//     }))
//   }));

//   source.addFeature(marker);
  
// });