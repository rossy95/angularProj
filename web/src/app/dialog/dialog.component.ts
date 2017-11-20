import {Component, OnInit, Inject, ChangeDetectorRef} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatSnackBar, MatSnackBarConfig} from '@angular/material';
import {FormControl, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';

import {Entry} from '../entry';
import {SnackbarComponent} from '../snackbar/snackbar.component'

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {
  entry: Entry;

  constructor(public dialogRef: MatDialogRef<DialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar, private cdr: ChangeDetectorRef) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  openSnackBar() {
    let config = new MatSnackBarConfig();
    config.duration = 1000;
    this.snackBar.open("Bitte geforderte Felder angeben", null, config);
  }

  addEntry(): void {
    if (this.entry.email && this.entry.name && this.entry.forename) {
      this.dialogRef.close(this.entry);
    }
    else {
      this.openSnackBar();
    }

  }


  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  matcher = new MyErrorStateMatcher();


  ngOnInit() {

    if (this.data && this.data.entry) {
      this.entry = this.data.entry;
    } else {
      this.entry = new Entry();
    }

  }
}
