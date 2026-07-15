
// advanced-sidebar.component.ts
import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, EventEmitter, Input, OnInit, Output, Renderer2 } from '@angular/core';
import { AuthserviceService } from '../../../../auth/service/authservice.service';
import { Route, Router } from '@angular/router';
import { HostListener } from '@angular/core';



@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css'],

})

export class SidebarComponent implements OnInit {
  isCollapsed=false;
  @Input() menu =
    {
      pageVideo:false,
      pageImage:false,
      pageDashboard:false,
      pageAficherImages:false,
      pageAficherVideos:false,
      pageSettings:false


    };
  @Output() showPageVideo = new EventEmitter<any>();

  dropdownOpen = false;



  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  // Fermer le dropdown en cliquant ailleurs
  ngOnInit() {
    document.addEventListener('click', (e) => {
      const target = e.target as HTMLElement;
      if (!target.closest('.user-dropdown')) {
        this.dropdownOpen = false;
      }
    });
  }
  // Menu items array
  menuItems = [
    {
      class: 'fas fa-video',
      label: 'Add Video',
      image: 'video-calling.png',
      link: '/video',
      active: false
    },
      {
      class: 'fas fa-image',
      label: 'Add Image',
      image: 'camera.png',
      link: '/image',
      active: false
    },
    {
      class: 'fas fa-chart-pie',
      label: 'Dashboard',
      image: 'data.png',
      link: '/dashboard',
      active: false,
    },
    {
      class: 'fas fa-images',
      label: 'Get Images',
      image: 'management.png',
      link: '/getImages',
      active: false
    },
     {
      class: 'fas fa-film',
      label: 'Get Videos',
      image: 'management.png',
      link: '/getVideos',
      active: false,
    },
    {
      class: 'fas fa-sliders-h',
      label: 'Settings',
      image: 'parametres.png',
      link: '/settings',
      active: false
    }

  ];

  constructor(
            private auth:AuthserviceService,
            private http:HttpClient,
            private router:Router,
            private elementRef: ElementRef,
            private renderer: Renderer2
  ) { }
  link!:string;
  item(){
    return  "this.link";
  }
  // Set active menu item
  setActive(item: any): void {

    this.menuItems.forEach(menuItem => menuItem.active = false);

      this.menu.pageVideo=false;
      this.menu.pageImage=false;
      this.menu.pageDashboard=false;
      this.menu.pageAficherImages=false;
      this.menu.pageAficherVideos=false;
      this.menu.pageSettings=false;

      item.active = !item.active;
    this.showPageVideo.emit(item.link);
  }

  test="";
  token(){
         this.auth.getToken().subscribe({
        next: x=>{
          this.test=x.token;
        },
        error: r=>{console.log("error",r)}
      });
  }


logout(){
  localStorage.removeItem("clients");
  localStorage.removeItem("villes");
  localStorage.removeItem("lieux");
  localStorage.removeItem("roles")
  location.href = "/pageLogout"
}

}
