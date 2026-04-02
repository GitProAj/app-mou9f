import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthontificationService } from 'src/app/service/authontification.service';

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.css']
})
export class AccueilComponent implements OnInit {

  isLoggedIn = false;
  userName = '';
  showUserMenu = false;
  showMobileMenu = false;

  constructor(
    private authService: AuthontificationService,
    private router: Router
  ) {}
  login(){
    this.authService.login();
  }
  ngOnInit() {
    // Subscribe to auth state changes
    // this.authService.getUser().subscribe(user => {
    //   this.isLoggedIn = !!user;
    //   if (user) {
    //     this.userName = user.message;
    //   }
    // });
  }

  toggleUserMenu() {
    this.showUserMenu = !this.showUserMenu;
  }

  toggleMobileMenu() {
    this.showMobileMenu = !this.showMobileMenu;
  }

  closeMobileMenu() {
    this.showMobileMenu = false;
  }

  // logout() {
  //   this.authService.logout();
  //   this.showUserMenu = false;
  //   this.router.navigate(['/']);
  // }

  // Close dropdown when clicking outside
  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    if (!event.target) return;
    
    const target = event.target as HTMLElement;
    if (!target.closest('.user-menu')) {
      this.showUserMenu = false;
    }
  }
}

