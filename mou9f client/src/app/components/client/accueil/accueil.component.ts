import { Component, OnInit, HostListener } from '@angular/core';
import { AuthserviceService } from '../../auth/service/authservice.service'; 





@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.css']
})
export class AccueilComponent implements OnInit{
   signupshow:boolean=false;

    constructor(private authService:AuthserviceService){

    }
    login(){
    this.authService.login();
  }
  ngOnInit(): void {
      console.log("les roles  :  ",this.authService.listRoles)
  }

}