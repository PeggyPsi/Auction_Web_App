import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { ActivatedRoute } from '@angular/router';
import { UserService, AlertService, AppService } from 'src/app/_services';

@Component({
  selector: 'app-admin-user-info',
  templateUrl: './admin-user-info.component.html',
  styleUrls: ['./admin-user-info.component.css']
})
export class AdminUserInfoComponent implements OnInit {
  
  user: User;
  message: string;

  constructor( private route: ActivatedRoute,
    private userService: UserService,
    private alertService: AlertService,
    // private router: Router,    
    private appService: AppService) {}      

  ngOnInit() {
    if (!localStorage.getItem("message")) localStorage.setItem("message", "");
    this.message = localStorage.getItem("message");
    localStorage.setItem("message", "");
    this.user = new User();
    // retrieve user id parameter from request url
    let publicId = this.route.snapshot.paramMap.get('publicId');
    // get user info
    this.userService.getUserById(publicId).subscribe(user => {
      this.user = user;
    });
  }

  // admin can either choose to accept users account or make it inactive
  public verifyUser(public_id: string, status: string){
    // console.log(public_id);
    this.userService.verifyUser(public_id, status)
    .subscribe(
      (response) => {
        //do something with the response
        // this.loadUsers();
        // console.log("Response is: ", response);
        if(status) localStorage.setItem("message", "User account has been accepted successfully");
        else localStorage.setItem("message", "User account has been cancelled");
        
        this.appService.refreshPage(["admin/userinfo", public_id]);
        // this.router.navigate(['', public_id], {relativeTo: this.route});
        // this.window.location.reload();
      },
      (error) => {
        //catch the error
        this.alertService.error(error);
        // console.error("An error occurred, ", error);
      }
    );
  }

  // TODO create delete user -> userService.deleteUser + spring deleteUser DELETE after i have created everything about items
  // TODO show also a message for whether the user is sure about this (it could be both the admin or the actual user)

}