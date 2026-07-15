import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthserviceService {
  
  // private authoritiesSubject = new BehaviorSubject<string[]>([]);
  // public listRole$ = this.authoritiesSubject.asObservable();
  public listRoles:string[]=[];
  public apiUrle = "/api/videos";
  private apiUrl = '/api/auth';

  constructor(private http: HttpClient, private router: Router) {
    
   }
  login(){
     window.location.href = '/oauth2/authorization/gateway';
    // Rediriger vers le Gateway qui gère l'OAuth2
  }

  
  getToken():Observable<any>{
     return this.http.get("/gatewayMou9f/token")

  }
  

  getRoles(){ 
      this.http.get<any>('/api/resource/getUsername').subscribe({
      next: roles=>{
        this.listRoles=roles;
        localStorage.setItem("roles",JSON.stringify(roles))
        console.log("kkkkkkkkkkkkkkkkk",this.listRoles)
      },
      error: error=>{console.log("error ",error)}
    })
  }

  // logout() {
  //   return this.http.post<{logoutUrl?: string}>(`${this.apiUrl}/logout`, {})
  // }

  getUserInfo(): void {
    this.http.get(`${this.apiUrl}/user-info`)
  }
  
  // Vérifie si l'utilisateur possède une autorité spécifique
  hasAuthority(authority: string):boolean {
    this.getRoles();
    if(this.listRoles.includes(authority)){
      return true;
    }
    return false;
  }

  // Vérifie si l'utilisateur possède au moins une des autorités
 hasAnyAuthority(authorities: string[]): boolean {
  
  if (!authorities || authorities.length === 0) {
    return false;
  }
  this.getRoles();
    const list = localStorage.getItem("roles");
    const roles: string[] = list ? JSON.parse(list) : [];
    console.log("athorities",authorities)
        console.log("roles",roles)
        console.log("roles fffffffffffffffffffffffffffffff",roles.some(role => authorities.includes(role)))
  return roles.some(role => authorities.includes(role));
 
}


   Authority(authorities: string[]): boolean {
  
  if (!authorities || authorities.length === 0) {
    return false;
  }
    
    // console.log("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",this.listRoles)
    // const list = localStorage.getItem("roles");
    const roles: string[] = ["USER"];
  return roles.some(role => authorities.includes(role));
 
}


}
