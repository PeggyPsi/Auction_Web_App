import { Item } from '../_models/item/item';

// update items whether auction for each one of them has ended
export class ItemsFunctions {
    static retrieveActive(items: Item[]): Item[] {
        for(let i=items.length-1 ; i>=0 ; --i) {
          if(items[i].active === false || items[i].ended === true) {
            console.log("remove: ", items[i].name, items[i].active, items[i].ended);
            items.splice(i, 1);
          }
        }
        return items;
    }

    static retrieveCompleted(items: Item[]): Item[] {
        for(let i=items.length-1 ; i>=0 ; --i) {
          if(items[i].ended === false) {
            // console.log("remove: ", items[i].name, items[i].started, items[i].ended);
            items.splice(i, 1);
          }
        }
        return items;
    }

    static retrieveActiveAndEnded(items: Item[]): Item[] {
      for(let i=items.length-1 ; i>=0 ; --i) {
        if(items[i].ended === false && items[i].active === false) {
          // console.log("remove: ", items[i].name, items[i].started, items[i].ended);
          items.splice(i, 1);
        }
      }
      return items;
  }

  static retrieveInactive(items: Item[]): Item[] {
    for(let i=items.length-1 ; i>=0 ; --i) {
      if(items[i].active === true) {
        // console.log("remove: ", items[i].name, items[i].started, items[i].ended);
        items.splice(i, 1);
      }
    }
    return items;
}
}
