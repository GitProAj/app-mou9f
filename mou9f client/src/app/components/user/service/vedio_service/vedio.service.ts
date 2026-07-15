import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Video } from '../../model/video';
  
@Injectable({
  providedIn: 'root'
})
export class VedioService {

  
     // URL de base de l'API Spring Boot
     // En développement, on utilise un proxy pour éviter les CORS
     private apiUrl = '/api/videos';  // Le proxy redirigera vers http://localhost:8080/api/videos
     
     constructor(private http: HttpClient) { }  // Injection du client HTTP
   
     /**
      * Upload une vidéo avec ses métadonnées
      * @param titre Titre de la vidéo
      * @param description Description
      * @param file Fichier vidéo
      * @returns Observable<Video> La vidéo créée
      */
     uploadVideo(titre: string, description: string, file: File): Observable<any> {
       // FormData est spécial pour l'envoi de fichiers multipart
       // Il permet d'envoyer à la fois des champs texte et des fichiers
       const formData: FormData = new FormData();
       
       // Append des données au FormData
      //  formData.append('username','user')
       formData.append('titre', titre);
       formData.append('description', description);
       formData.append('fichier', file);  // 3ème paramètre = nom du fichier
       
       // Envoi POST avec FormData
       // Content-Type sera automatiquement multipart/form-data avec boundary
       return this.http.post<any>(`${this.apiUrl}/upload`, formData)
         .pipe(
           retry(1),  // Réessaie 1 fois en cas d'échec
           catchError(this.handleError)  // Gestion des erreurs
         );
     }
   
     /**
      * Récupère toutes les vidéos
      * @returns Observable<Video[]> Liste des vidéos
      */
     getAllVideos(): Observable<Video[]> {
       return this.http.get<Video[]>(this.apiUrl)
         .pipe(
           catchError(this.handleError)
         );
     }
   
     /**
      * Récupère une vidéo par son ID
      * @param id ID de la vidéo
      * @returns Observable<Video> La vidéo
      */
     getVideoById(id: number): Observable<Video> {
       return this.http.get<Video>(`${this.apiUrl}/${id}`)
         .pipe(
           catchError(this.handleError)
         );
     }
   
     /**
      * Supprime une vidéo
      * @param id ID de la vidéo à supprimer
      * @returns Observable<any> Résultat de la suppression
      */
     deleteVideo(id: number): Observable<any> {
       return this.http.delete(`${this.apiUrl}/${id}`)
         .pipe(
           catchError(this.handleError)
         );
     }
   
     /**
      * Obtient l'URL de streaming pour une vidéo
      * @param id ID de la vidéo
      * @returns string URL de streaming
      */
     getStreamingUrl(id: number): string {
       return `${this.apiUrl}/${id}/stream`;
     }
   
     /**
      * Gestionnaire d'erreurs HTTP
      * @param error L'erreur HTTP
      * @returns Observable avec l'erreur
      */
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
   
      getUser():Observable<any>{
       return this.http.get(`${this.apiUrl}/user`);
         // .pipe(
         //   catchError(this.handleError)
         // );
     }
   
     test(titre:string,description:string):Observable<any>{
   
     const formData: FormData = new FormData();
       
       // Append des données au FormData
       formData.append('titre', titre);
       formData.append('description', description);
       
      return this.http.post<any>(`${this.apiUrl}/test`, formData)
         .pipe(
           retry(1),  // Réessaie 1 fois en cas d'échec
           catchError(this.handleError)  // Gestion des erreurs
         );
       }
   }
   
   
