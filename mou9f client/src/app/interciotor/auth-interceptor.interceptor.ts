import { Injectable, } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable,catchError,throwError } from 'rxjs';
import { AuthserviceService } from '../components/auth/service/authservice.service';

@Injectable()
export class AuthInterceptorInterceptor implements HttpInterceptor {

  constructor(private auth:AuthserviceService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 ) {
          // Force a full browser redirect to the gateway's login endpoint
          console.warn('Authentication required. Redirecting to login...');
          window.location.href = '/oauth2/authorization/gateway';
        }
        return throwError(() => error);
      })
    );
  }
}
