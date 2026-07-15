import { Component } from '@angular/core';
import { window } from 'rxjs';
import { VedioService } from './components/user/service/vedio_service/vedio.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mou9f-client';
  usern!:string;
  testt!:string;
  constructor(private useratr:VedioService){}
   
  isSidebarCollapsed = false;

  onSidebarCollapsedChange(collapsed: boolean) {
    this.isSidebarCollapsed = collapsed;
  }

  
  user():void{
    this.useratr.getUser().subscribe(
      data => {
        this.usern = data.message;
      },
      eror=>{console.log("ereeeeeeeeeeeeeeeeeerere",eror)}
      // {
      //   next:(data=>{this.usern=data}),
      //   error:(er=>{console.log(er)})
      // }
    )
  }

    test(){
    this.useratr.test("yousef","ahmed").subscribe(
      data => {
        this.testt = data.message;
      },
      eror=>{console.log("ereeeeeeeeeeeeeeeeeerere",eror)}
      // {
      //   next:(data=>{this.usern=data}),
      //   error:(er=>{console.log(er)})
      // }
    )
  }
}
