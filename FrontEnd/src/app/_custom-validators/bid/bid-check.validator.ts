import { AbstractControl, ValidatorFn } from '@angular/forms';

export class BidCheckValidator {
  static bidCheckValidator(currentBid: number): ValidatorFn {
    return (control: AbstractControl) => {
      let newBid = control.value;
      // new Bid must be larger than current
      if (newBid > currentBid || newBid === "") return null;
      // Validation Error
      else return {BidCheck: true};
    };
  }
}
