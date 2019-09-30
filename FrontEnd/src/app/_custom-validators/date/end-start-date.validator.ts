import { AbstractControl } from '@angular/forms';
import { DateCompare } from 'src/app/_functions/date-compare';

export class EndStartDateValidator {
    static endStartDateValidator(control: AbstractControl) {
      // Everything fine
      if (DateCompare.dateCompare(control.get('auctionStart').value, control.get('auctionEnd').value) == -1) {
        return null;
      }
      // Validation error
      else {
        control.get('auctionEnd').setErrors({ DatesValid: true });
      }
    }
}
