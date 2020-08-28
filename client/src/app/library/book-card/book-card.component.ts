import { Component, OnInit, Input } from '@angular/core';
import { Book } from 'src/app/shared/model/book';
import { HttpClientService } from 'src/app/shared/service/http-client.service';
import { BOOK_API_URL } from 'src/app/shared/urls';

@Component({
  selector: 'book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.css']
})
export class BookCardComponent implements OnInit {

  @Input() book: Book;

  img;
  isImgLoading: boolean;
  link;

  constructor(private http: HttpClientService) { }

  ngOnInit() {
    this.getImage();
    this.link = BOOK_API_URL + "/" + this.book.id + "/get-pdf";
  }

  getImage() {
    this.isImgLoading = true;
    this.http.getImg(BOOK_API_URL + "/" + this.book.id + "/get-thumbnail").subscribe(
      data => {
        this.convertImageToBase64(data);
      }, (err: any) => {
        console.log(err);
      }
    );
  }

  convertImageToBase64(image) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.img = reader.result;
      this.isImgLoading = false;
    }, false);

    if(image) {
      reader.readAsDataURL(image);
    }
  }

}
