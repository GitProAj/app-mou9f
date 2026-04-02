import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthontificationService {
  
  public apiUrle = "/api/videos";
  private apiUrl = '/api/auth';

  constructor(private http: HttpClient, private router: Router) { }
  login(){
     location.href = '/oauth2/authorization/gateway';
    //  this.router.navigate(['/oauth2/authorization/gateway'])
  }

  
     getUser():Observable<any>{
      return this.http.get(`${this.apiUrl}/user`);
        // .pipe(
        //   catchError(this.handleError)
        // );
    }

  user$ = new BehaviorSubject<any>(null);


  logout() {
    return this.http.post<{logoutUrl?: string}>(`${this.apiUrl}/logout`, {})
    // .subscribe(
    //   {
    //     next:(res)=> {
    //       this.user$.next(null);
    //       if (res.logoutUrl) window.location.href = res.logoutUrl;
    //       else this.router.navigate(['/login']);
    //     },
    //     error: (er)=>{
    //       console.log("errour ",er)
    //     }
    //   }
    // )
  }

  getUserInfo(): void {
    this.http.get(`${this.apiUrl}/user-info`)
    // .subscribe({
    //   next: (user) => this.user$.next(user),
    //   error: (err) => console.error('Erreur', err)
    // });
  }
}


