import { AbstractControl } from '@angular/forms';

export class InitialBuyPriceValidator {
    static initialBuyPriceValidator(control: AbstractControl) {
      // Everything fine
      if (control.get('firstBid').value < control.get('buyOffer').value) {
        return null;
      }
      // Validation error
      else {
        control.get('buyOffer').setErrors({ IntitialAndBuy: true });
      }
    }
}
