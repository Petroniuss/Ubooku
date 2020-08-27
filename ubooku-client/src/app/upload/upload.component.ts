import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { FILE_UPLOAD_URL } from '../shared/urls';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  constructor(private http: HttpClient) { }

  categories = ['Documentary',
    'Fantasy',
    'Fiction',
    'Comics',
    'Educational',
    'History',
    'Horror',
    'Science',
    'Coding',
    'Cooking'
  ];
  
  bookForm = new FormGroup({
    title: new FormControl(''),
    author: new FormControl(''),
    categories: new FormControl(''),
    pdfFile: new FormControl('')
  })


  loading: boolean = false;
  loadedFile;
  info;

  ngOnInit() {
  }

  onSubmit() {
    console.warn(this.bookForm.value);

    var formData = new FormData();

    console.log(this.loadedFile);
    formData.append("file", this.loadedFile);
    var json = JSON.stringify({
      'title': this.bookForm.value.title,
      'author': this.bookForm.value.author,
      'categories': this.bookForm.value.categories
    });
    
    var blob = new Blob([json], {type: "application/json"});
    formData.append('details', blob);
    
    this.loading = true;
    this.http.post(FILE_UPLOAD_URL, formData).subscribe((res: any) => {
      this.loading = false;
      this.info = res.message;
      setTimeout(() => this.info = null, 4000);
    }, (err: any) => {
      this.loading = false;
      this.info = err.error.message;
      if(!this.info) {
        this.info = err.message;
      }
      console.log(err);
      setTimeout(() => this.info = null, 4000);
    })
  }

  onFileChange(event) {

    if(event.target.files && event.target.files.length) {
      this.loadedFile = event.target.files[0];
      
    }
  }

}
