import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Book } from '../shared/model/book';
import { HttpClientService } from '../shared/service/http-client.service';
import { BOOK_API_URL } from '../shared/urls';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent implements OnInit {


  uploadedBooks: Book[];
  form;

  categories = [
    {cat: 'Documentary', selected: false},
    {cat: 'Fantasy', selected: false},
    {cat: 'Fiction', selected: false},
    {cat: 'Comics', selected: false},
    {cat: 'History', selected: false},
    {cat: 'Educational', selected: false},
    {cat: 'Horror', selected: false},
    {cat: 'Science', selected: false},
    {cat: 'Coding', selected: false},
    {cat: 'Cooking', selected: false},
  ];

  constructor(
    private http: HttpClientService, // simply inject auth header
    private fb: FormBuilder
    ) { 
      this.form = this.fb.group({
        categories: this.buildCategories()
      });
  }

  buildCategories() {
    const arr = this.categories.map(category => {
      return this.fb.control(category.selected);
    });

    return this.fb.array(arr);
  }

  ngOnInit() {
    this.uploadAll();
  }

  onSubmit(value) {
    let query: String = new String(BOOK_API_URL + "/search/categories?");
    for(var i = 0; i < this.categories.length; i++) {
        var c = this.categories[i];
        if(c.selected) {
          query = query.concat("cat=" + c.cat + "&");
        }
    }
    console.log(query.toString());
    if(query.toString()===BOOK_API_URL + "/search/categories?"){
      this.uploadAll(); 
    } else {
    this.http.get(query.toString()).subscribe((res: Book[]) => {
      this.uploadedBooks = res;
    })
  }
  }

  toggle(cat) {
    cat.selected = !cat.selected;
  }

  onSearch(event) {
    if(event === ""){
      this.uploadAll();
    } else {
      this.http.get(BOOK_API_URL + "/search" + "?query=" + event).subscribe((res: Book[]) => {
        this.uploadedBooks = res;
      })
    }
  }
  //To be replaced by upload(page, pageSize) --> PAGEABLE 
  uploadAll() {
    this.http.get(BOOK_API_URL + "/all").subscribe(
      (res: Book[]) => {
        console.log(res);
        this.uploadedBooks = res
      }, (err: any) => {
        console.log(err);
      }
    );
  }


}
