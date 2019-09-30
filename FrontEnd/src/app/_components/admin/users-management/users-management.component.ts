import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/_models/user';
import { UserService, AlertService, AuthenticationService, AppService } from 'src/app/_services';
import { Router, ActivatedRoute } from '@angular/router';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-users-management',
  templateUrl: './users-management.component.html',
  styleUrls: ['./users-management.component.css']
})
export class UsersManagementComponent implements OnInit {

  users: User[];
  currentHome: String;

  constructor(private userService: UserService, 
    private alertService: AlertService, 
    private authenticationService: AuthenticationService,
    private router: Router,
    private route: ActivatedRoute,
    private appService: AppService
  ) { }

  ngOnInit() {
    this.users = [];
    this.authenticationService.currentHome.subscribe(x => this.currentHome = x);
    this.loadUsers();
  }

  public loadUsers() {
    this.userService.getAll().pipe(first()).subscribe(users => {
      this.users = users;
    });
  }

  // admin can either choose to accept users account or make it inactive
  public verifyUser(public_id: string, status: string, index: number){
    console.log(public_id);
    this.userService.verifyUser(public_id, status)
    .subscribe(
      (response) => {
        this.alertService.clearMessage();
        //do something with the response
        // this.loadUsers();
        // console.log("Response is: ", response);
        if(status) localStorage.setItem("message", "User account has been accepted successfully");
        else localStorage.setItem("message", "User account has been declined");

        // if(status === "true") {
        //   this.alertService.success("User account is accepted successfully", true);
        // }
        // else if(status === "false"){
        //   this.alertService.success("User account is declined successfully", true)
        // };
        
        this.appService.refreshPage(["/admin/users"]);
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

  // public showUserInfo(public_id: string) {
  //   this.router.navigate(['/admin/users/info', public_id], {relativeTo: this.route});
  // }

}
