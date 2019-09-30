import { Component, OnInit, Input } from '@angular/core';
import { Item } from 'src/app/_models/item/item';

declare var ol: any;

// Elements that make up the popup.
var container;
var content;
var popup;
var longitude;
var latitude;
var markerLayer;

@Component({
  selector: 'app-map-item-details',
  templateUrl: './map-item-details.component.html',
  styleUrls: ['./map-item-details.component.css']
})
export class MapItemDetailsComponent implements OnInit {

  @Input() item: Item;

  map: any;
  
  constructor() { }

  ngOnInit() {
    longitude = this.item.location.longitude;
    latitude = this.item.location.latitude;
    container = document.getElementById('popup');
    content = document.getElementById('popup-content');
    console.log(content);
    this.setPopup();
    this.setMarker();
    this.loadMap();
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

  setMarker(){
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

    markerLayer = new ol.layer.Vector({
      source: new ol.source.Vector({
          features: [
            marker
          ]
        })
    });
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
  }

  // setCenter() {
  //   var view = this.map.getView();
  //   let longitude = this.item.location.longitude;
  //   let latitude = this.item.location.latitude;
  //   console.log(longitude + " " + latitude)
  //   view.setCenter(ol.proj.fromLonLat([longitude, latitude]));
  //   view.setZoom(8);
  //   view.marker;
  // }

      // // Popup showing the position the user clicked
    // var popup = new ol.Overlay({
    //   element: document.getElementById('popup')
    // });
    // this.map.addOverlay(popup);

      // // if item has been given set marker
    // if(this.item) this.setMarker();
    // // not item has been given -> item is being created
    // else {
      // on click
      // this.map.on('click', function (args) {
      //   console.log(args.coordinate);
      //   var lonlat = ol.proj.transform(args.coordinate, 'EPSG:3857', 'EPSG:4326');
      //   console.log(lonlat);
        
      //   var lon = lonlat[0];
      //   var lat = lonlat[1];
      //   // alert(`lat: ${lat} long: ${lon}`);
      // });
    // }

        // this.map.on('load', function(evt) {
      // var coordinate = evt.coordinate;
      // var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(
      //     coordinate, 'EPSG:3857', 'EPSG:4326'));
      // content.innerHTML = '<p>You clicked here:</p><code>' + longitude +
      //     '</code>';
      //     console.log(coordinate);
      // popup.setPosition(ol.proj.fromLonLat([longitude, latitude]));
    // });

}