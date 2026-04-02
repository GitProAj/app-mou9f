import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { filter } from 'rxjs';
import { AuthontificationService } from 'src/app/service/authontification.service';
// import { NavigationEnd ,ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})

export class LogoutComponent implements OnInit {
  username: string | null = null;
  loading = false;
  isLoggedOut = false;
  
  constructor(
    private http: HttpClient,
    private router: Router
  ) {}
  
  ngOnInit() {
 
  }
  
  logout() {    
    this.http.get<any>('/api/auth/logout')
      .subscribe({
        next: (response) => {
          this.isLoggedOut = true;
          setTimeout(() => {
            window.location.href = response.logoutUrl;
          }, 1500);
          console.log("responseeeeeeeeee",response)
        },
        error: (error) => {
          console.error('Logout failed', error);
          this.loading = false;
          // alert('Erreur lors de la déconnexion');
          alert(error.message);

        }
      });
  }
  
  cancel() {
    this.router.navigate(['/']);
  }
}

