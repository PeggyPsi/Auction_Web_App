import { Item } from '../item/item';
import { User } from '../user';

export class Bid {
    id: number;
    item: Item;
    bidder: User;
    amount: number;
    time: Date;
    winStatus: string;

    constructor() {
        this.bidder = new User();
        this.item = new Item();
    }
}
