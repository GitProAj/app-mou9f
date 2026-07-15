import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientMou9f } from '../client-mou9f';

export interface Image{
    id:number,
    idClient: string,
    title: string,
    description: string,
    bytesImage: string,
    contentTypeImage: string,
    sizeImage: string,
    dateUpload: string,
}
// video.model.ts
export interface Video {
    id: number;
    titre: string;
    description: string;
    idClient: string;
    typeVideo: string;
    tailleVideo: number;
    contenuVideo: string; // Base64 ou byte array
    




}
@Injectable({
  providedIn: 'root'
})
export class ServiceClientService {
  private apiUrl = '/open'
  constructor(private http:HttpClient) { }

     getAllActivites(): Observable<string[]> {
        return this.http.get<string[]>(`${this.apiUrl}/activites`);
    }


     getVillesByActivite(activite: string): Observable<string[]> {
        console.log(`${this.apiUrl}/villes?activite=${activite}`)
        return this.http.get<string[]>(`${this.apiUrl}/villes?activite=${activite}`);
    }


    // Récupérer les lieux par activité et ville
    getClientsFilteredByActiviteAndVille(activite: string, ville: string): Observable<ClientMou9f[]> {
        return this.http.get<ClientMou9f[]>(`${this.apiUrl}/clients?activite=${activite}&ville=${ville}`);
    }

    // Récupérer les clients filtrés
    // getClientsFiltered(activite?: string, ville?: string, lieut?: string): Observable<ClientMou9f[]> {
    //     let params: any = {};
    //     if (activite) params.activite = activite;
    //     if (ville) params.ville = ville;
    //     if (lieut) params.lieut = lieut;
        
        // return this.http.get<ClientMou9f[]>(`${this.apiUrl}/clients/filter`, { params });
          // return this.http.get<ClientMou9f[]>(`${this.apiUrl}/clients?activite=${activite}&ville=${ville}&lieut=${lieut}`);

    // }

    // video.service.ts


  
  private apiUrll = '/api/videos';
  
  
  // Récupérer TOUTES les vidéos
  getAllVideos(): Observable<any> {
    return this.http.get(`${this.apiUrl}/all`);
  }
  
  // Récupérer l'URL de streaming pour une vidéo
  getVideoStreamUrl(videoId: number): string {
    return `${this.apiUrll}/stream/${videoId}`;
  }
  
  // Incrémenter le nombre de vues
  incrementViews(videoId: number): Observable<any> {
    return this.http.post(`${this.apiUrll}/${videoId}/view`, {});
  }

}
