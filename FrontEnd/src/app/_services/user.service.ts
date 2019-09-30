import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { User } from '../_models/user';

@Injectable({ providedIn: 'root' })
export class UserService {
    private usersUrl: string;

    constructor(private http: HttpClient) {
      this.usersUrl = 'https://localhost:8443';
    }

    getAll() {
        return this.http.get<User[]>(this.usersUrl + '/users/all');
    }

    register(user: User) {
        return this.http.post(this.usersUrl+'/register', user);
    }

    verifyUser(public_id: string, status: string) {
        // console.log(public_id);
        return this.http.put(this.usersUrl + '/users/verify', "", {
            params: new HttpParams().set('id', public_id).set('status', status)              // https://localhost:8443/users/verify?id=something
        });
    }

    // get parameter username and send a request to rest api to return to you
    // the corresponding user, if one exists
    getUserByUsername(username: string) {
        return this.http.get<User>(this.usersUrl+'/users/username/'+username);
    }

    getUserById(public_id: string) {
        return this.http.get<User>(this.usersUrl + '/users/id/'+public_id);
    }

    // update(user: User) {
    //     return this.http.put(`this.usersUrl/${user.public_id}`, user);
    // }

    // delete(public_id: string) {
    //     return this.http.delete(`this.usersUrl/${public_id}`);
    // }
}