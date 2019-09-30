import { ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { UserService } from 'src/app/_services/user.service';

export class UniqueUsernameValidator {

    static uniqueUsernameValidator(userService: UserService): ValidatorFn{
        return (control: AbstractControl) => {
                if (!control.value) {
                    // if control is empty return no error
                    return null;
                }
                
                userService.getUserByUsername(control.value).subscribe(
                    data => {
                        if (data) control.setErrors({uniqueUsername: true});
                    }
                )
            }
      }
}
