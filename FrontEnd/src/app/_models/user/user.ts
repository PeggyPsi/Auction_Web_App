// Basic user model for the application

import {Location} from '../location/location'

export class User {
    id: number;
    username: string;
    password: string;
    password_confirm: string;
    publicId: string;
    fname: string;
    lname: string;
    email: string;
    phone: string;
    afm: string;
    role: string;
    location: Location;
    jwtToken: string;
    sellerRating: number;
    buyerRaing: number;
    verified: boolean;

    constructor(){
        this.location = new Location();
    }
}