
// advanced-sidebar.component.ts
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';



@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css'],

})

export class SidebarComponent implements OnInit {
  @Input() menu = 
    {
      pageVideo:false,
      pageImage:false,
      pageUsers:false,
      pageSetting:false

    };
  @Output() showPageVideo = new EventEmitter<any>();
  // Menu items array
  menuItems = [
    { 
      label: 'Add Video', 
      image: 'video-calling.png', 
      link: '/video',
      active: false 
    },
      { 
      label: 'Add Image', 
      image: 'camera.png', 
      link: '/image',
      active: false 
    },
    { 
      label: 'Dashboard', 
      image: 'data.png', 
      link: '/dashboard',
      active: false 
    },
    { 
      label: 'Users', 
      image: 'management.png', 
      link: '/users',
      active: false 
    },
    { 
      label: 'Settings', 
      image: 'parametres.png', 
      link: '/settings',
      active: false 
    },
   
  ]; 
  
  @Input() isCollapsed= false;
  @Output() collapsedChange = new EventEmitter<boolean>();


  constructor() { }

  ngOnInit(): void {
  }


  // Set active menu item
  setActive(item: any): void {

    this.menuItems.forEach(menuItem => menuItem.active = false);
    
      this.menu.pageVideo=false;
      this.menu.pageImage=false;
      this.menu.pageUsers=false;
      this.menu.pageSetting=false;


    item.active = !item.active;

    if(item.link=="/video"){

      this.menu.pageVideo=true;

    }else if(item.link=="/image")
    {
      this.menu.pageImage=true;

    }else if(item.link=="/users")
    {
      this.menu.pageUsers=true;

    }else if(item.link=="/settings")
    {
      this.menu.pageSetting=true;

    }

    // this.menu=!this.menu;
    this.showPageVideo.emit(this.menu);
  }
   toggleSidebar() {
    this.isCollapsed = !this.isCollapsed;
    this.collapsedChange.emit(this.isCollapsed);
  }
}