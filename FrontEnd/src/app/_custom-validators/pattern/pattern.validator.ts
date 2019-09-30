import { ValidationErrors, ValidatorFn, AbstractControl } from '@angular/forms';

export class PatternValidator {
    static patternValidator(regex: RegExp, error: ValidationErrors): ValidatorFn{
        return (control: AbstractControl) => {
              if (!control.value) {
                // if control is empty return no error
                return null;
              }
          
              // test the value of the control against the regexp supplied
              const valid = regex.test(control.value);

              if(!valid){
                return error;
              }
              else return null;
          };
      }
}
