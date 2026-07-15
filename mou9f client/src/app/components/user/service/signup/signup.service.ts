import { HttpClient } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { catchError, Observable, retry, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SignupService {

  constructor(private http:HttpClient) { }

  addClient(data:any):Observable<any>{
    return this.http.post<any>("/open/addClient",data)
            .pipe(
              retry(1),
              catchError(this.handleError)
            );
    
  }

  createAcount(data:any):Observable<any>{
    return this.http.post<any>("http://localhost:9000/adduser",data)
              .pipe(
                retry(1),
                catchError(this.handleError)
              );
  }

    private handleError(error: HttpErrorResponse) {
         let errorMessage = 'Une erreur est survenue';
         
         if (error.error instanceof ErrorEvent) {
           // Erreur côté client
           errorMessage = `Erreur: ${error.error.message}`;
         } else {
           // Erreur côté serveur
           errorMessage = `Code: ${error.status}, Message: ${error.message}`;
         }
         
         console.error(errorMessage);
         return throwError(() => new Error(errorMessage));
       }
}
