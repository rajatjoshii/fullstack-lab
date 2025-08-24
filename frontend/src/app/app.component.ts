import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'angular-14-crud-example';
  user = {name:'Rajat', password:'123'};

  constructor(private http: HttpClient){}

  ngOnInit(): void {
      console.log("This is docker running")
     this.http.post("/api/users",this.user).subscribe((value)=>console.log(value));
  }
}
