import { HttpClient } from '@angular/common/http';
import { Component, OnInit,OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { ClientMou9f } from '../client-mou9f';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
// import { VideoService } from '../services/video.service';
import { ServiceClientService, Video,Image } from '../service/service-client.service'; 

@Component({
  selector: 'app-detailclient',
  templateUrl: './detailclient.component.html',
  styleUrls: ['./detailclient.component.css']
})
export class DetailclientComponent implements OnInit  {

  constructor(private activitedroute:ActivatedRoute,
    private http:HttpClient,
    private sanitizer: DomSanitizer,
    private serviceclient:ServiceClientService,
    private router : Router
  ){}
  showclient=true;
  id!:string;
  images:Image[]=[];
  videos: Video[] = [];
  userInfo!:ClientMou9f;
  storedUser!:ClientMou9f[];

  itemshowpage = {
      showVideo:false,
      showImage:false,
      userRole:false

  }

  ngOnInit(): void {
       this.activitedroute.paramMap.subscribe(
        param => {
          this.id = param.get('id') || '';
          this.loadUserInfo();
        }
      )

  }

  
  goToHomePage(): void {
    this.router.navigate(['/home/accueil']);
  
  }

    // Récupérer les initiales pour l'avatar
  getUserInitials(): string {
    if (!this.userInfo) return '?';
    return `${this.userInfo.firstname.charAt(0)}${this.userInfo.lastname.charAt(0)}`;
  }

  showvideos(){
    // if(!this.userInfo){return}
      this.itemshowpage.showVideo = true;
      this.itemshowpage.showImage = false; 
      this.showclient=false;
    
  }
  showimages(){
    // if(!this.userInfo){return}
      this.itemshowpage.showVideo=false;
      this.itemshowpage.showImage=true; 
      this.showclient=false; 
  }

  // Charger les informations de l'utilisateur connecté
  loadUserInfo(): void {
  // Récupérer depuis localStorage, sessionStorage ou un service
     const storedUser = localStorage.getItem('clients');
     console.log("localstor",storedUser)
      if (storedUser) {
        this.storedUser = JSON.parse(storedUser);
        this.storedUser.forEach(element => {
          if(element.id == Number(this.id)){
            this.userInfo = element;
            console.log("userinfo",this.userInfo);
          }

         });
      } 
  }

}





