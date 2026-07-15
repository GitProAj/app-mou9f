import { CanActivateFn, Router } from '@angular/router';
import { AuthserviceService } from '../service/authservice.service';
import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { window } from 'rxjs';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthserviceService);
  const router =inject(Router)
  const expectedRole = route.data['roles'];   

  console.log("data roles auth guard : ",expectedRole);
  console.log("data roles auth guard : ",authService.hasAnyAuthority(expectedRole));

   if(authService.hasAnyAuthority(expectedRole)){
    return true;
   }

    authService.login();
   return false;

};
