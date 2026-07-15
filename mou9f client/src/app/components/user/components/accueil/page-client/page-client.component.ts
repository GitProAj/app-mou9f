import { Component , Input, Output , EventEmitter, OnInit } from '@angular/core';
import { AuthserviceService } from '../../../../auth/service/authservice.service'; 
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page-client',
  templateUrl: './page-client.component.html',
  styleUrls: ['./page-client.component.css']
})
export class PageClientComponent implements OnInit {
  constructor(private router:Router, private http:HttpClient ,private auth:AuthserviceService){}

  ngOnInit(): void {
    this.auth.getRoles();
      console.log("nnnnnnnnnnnnnnnnnnnn",this.auth.listRoles[0])
  }
  
  public menu = 
    {
      pageVideo:false,
      pageImage:false,
      pageDashboard:false,
      pageAficherImages:false,
      pageAficherVideos:false,
      pageSettings:false

    };

     public showimgvideo = 
    {
      showVideo:false,
      showImage:false,
      userRole:false
    };

  
  show(item:string){
      this.showimgvideo.showVideo=false;
      this.showimgvideo.showImage=false;
      
    if(item=="/video"){
      this.menu.pageVideo=true;

    }else if(item=="/image")
    {
      this.menu.pageImage=true;
    }else if(item=="/getImages")
    { 
      this.menu.pageAficherImages=true;
      this.showimgvideo.showImage=true;
      this.showimgvideo.userRole=true;
    }else if(item=="/getVideos"){
      this.menu.pageAficherVideos=true;
      this.showimgvideo.showVideo=true;
      this.showimgvideo.userRole=true;


    }else if(item=="/settings")
    {
      this.menu.pageSettings=true;

    }else if(item=="/dashboard")
    {
      this.router.navigate(["/home/accueil"]);    
    }
    
     
  }
}
