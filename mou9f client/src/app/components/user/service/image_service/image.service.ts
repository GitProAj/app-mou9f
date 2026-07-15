 import { Injectable } from '@angular/core';
  import { HttpClient, HttpEventType, HttpErrorResponse } from '@angular/common/http';
  import { Observable, throwError } from 'rxjs';
  import { map, catchError } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class ImageService {

  
    private apiUrl = '/api/resource/uploadImage';
  
    constructor(private http: HttpClient) {}
  
    /**
     * Upload d'image
     */
    
    uploadImage(formData: FormData): Observable<any> {
      return this.http.post(this.apiUrl, formData, {
        reportProgress: true,
        observe: 'events'
      }).pipe(
        map(event => {
          switch (event.type) {
            case HttpEventType.UploadProgress:
              const progress = event.total 
                ? Math.round(100 * event.loaded / event.total)
                : 0;
              return { status: 'progress', progress };
              
            case HttpEventType.Response:
              return { status: 'completed', data: event.body };
              
            default:
              return { status: 'other', event };
          }
        }),
        catchError(this.handleError)
      );
     }
  
    /**
     * Redimensionnement d'image côté client (optionnel)
     */
    async resizeImage(file: File, maxWidth: number, maxHeight: number): Promise<Blob> {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        
        reader.onload = (event) => {
          const img = new Image();
          img.src = event.target?.result as string;
          
          img.onload = () => {
            // Calculer les nouvelles dimensions
            let width = img.width;
            let height = img.height;
            
            if (width > height) {
              if (width > maxWidth) {
                height *= maxWidth / width;
                width = maxWidth;
              }
            } else {
              if (height > maxHeight) {
                width *= maxHeight / height;
                height = maxHeight;
              }
            }
            
            // Créer le canvas pour le redimensionnement
            const canvas = document.createElement('canvas');
            canvas.width = width;
            canvas.height = height;
            
            const ctx = canvas.getContext('2d');
            ctx?.drawImage(img, 0, 0, width, height);
            
            // Convertir en blob
            canvas.toBlob((blob) => {
              if (blob) {
                resolve(blob);
              } else {
                reject(new Error('Erreur lors du redimensionnement'));
              }
            }, file.type);
          };
          
          img.onerror = () => {
            reject(new Error('Erreur lors du chargement de l\'image'));
          };
        };
        
        reader.onerror = () => {
          reject(new Error('Erreur lors de la lecture du fichier'));
        };
      });
    }
  
    /**
     * Gestion des erreurs
     */
    private handleError(error: HttpErrorResponse) {
      let errorMessage = 'Erreur inconnue';
      
      if (error.error instanceof ErrorEvent) {
        // Erreur client
        errorMessage = `Erreur: ${error.error.message}`;
      } else {
        // Erreur serveur
        switch (error.status) {
          case 400:
            errorMessage = 'Données invalides';
            break;
          case 413:
            errorMessage = 'Image trop volumineuse';
            break;
          case 415:
            errorMessage = 'Format d\'image non supporté';
            break;
          case 500:
            errorMessage = 'Erreur serveur';
            break;
          default:
            errorMessage = `Erreur ${error.status}: ${error.message}`;
        }
      }
      
      return throwError(() => new Error(errorMessage));
    }
  }
  
  