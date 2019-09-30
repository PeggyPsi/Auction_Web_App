import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class FixedLengthValidator {
    static fixedLengthValidator(length: number): ValidatorFn {
        return (control: AbstractControl) => {
            // no value has been assigned to the field
            if (!control.value) return null;
            // input is not fixed size
            if (control.value.length != length) return {fixedLength: true};
            else return null;
        };
    }
}

