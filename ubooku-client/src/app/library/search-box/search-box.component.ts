import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'search-box',
  templateUrl: './search-box.component.html',
  styleUrls: ['./search-box.component.css']
})
export class SearchBoxComponent implements OnInit {

  constructor() { }


  @Output() search: EventEmitter<string> = new EventEmitter();

  ngOnInit() {
    
  }

  onEnter(value: string) {
    this.search.emit(value);
  }

}
