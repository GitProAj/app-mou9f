import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, window } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { filter } from 'rxjs';
import { AuthserviceService } from '../auth/service/authservice.service'; 
import { FormGroup,FormBuilder } from '@angular/forms';
import { NavigationEnd ,ActivatedRoute } from '@angular/router';
import { LoginRequest } from '../auth/model/LoginRequest';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})


export class LogoutComponent implements OnInit {


  ngOnInit() {
    
     location.href = '/oauth2/authorization/gateway';
  }

 
}



 
