import { Category } from '../category/category';
import { User } from '../user';
import { Image } from './images/image';
import { Location } from '../location/location'

export class Item {
    id: number;
    name: string;           //new item
    description: string;        // new item
    firstBid: number;           // new item
    currentBid: number;
    buyOffer: number;
    auctionStart: Date = new Date();         // new item
    auctionEnd: Date = new Date();           // new item
    noBids: number;
    location: Location = new Location();         // new item
    seller: User = new User();               // new item
    buyer: User = new User();
    buyPrice: number;
    categories: Category[] = [];     // new item
    images: Image[] = [];
    ended: boolean;
    active: boolean = false;

    constructor() {
        this.seller = new User();
        this.buyer = new User();
        this.location = new Location();
    }
}
