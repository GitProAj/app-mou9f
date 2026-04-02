import { Component , Input, Output , EventEmitter } from '@angular/core';
import { AuthontificationService } from 'src/app/service/authontification.service';

@Component({
  selector: 'app-page-client',
  templateUrl: './page-client.component.html',
  styleUrls: ['./page-client.component.css']
})
export class PageClientComponent {
  constructor(private authentification: AuthontificationService){}
  
  public showPage = 
    {
      pageVideo:false,
      pageImage:false,
      pageUsers:false,
      pageSetting:false

    };
  // @Input() isCollapsed = false;
  // @Output() collapsedChange = new EventEmitter<boolean>();

  // toggleSidebar() {
  //   this.isCollapsed = !this.isCollapsed;
  //   this.collapsedChange.emit(this.isCollapsed);
  // }

  logout(){
     location.href="/logout";
  }
  show(etat:[]){
    etat.forEach(item=> {
      if(item=='pageVideo'){
       this.showPage.pageVideo=item;
      }
       if(item=='pageImage'){
       this.showPage.pageImage=item;
      }
       if(item=='pageusers'){
       this.showPage.pageUsers=item;
      }
       if(item=='pageSettings'){
       this.showPage.pageSetting=item;
      }
    
    })
  }
}
