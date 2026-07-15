import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { ClientMou9f } from '../client-mou9f';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
// import { VideoService } from '../services/video.service';
import { ServiceClientService, Video,Image } from '../service/service-client.service'; 



@Component({
  selector: 'app-page-images-videos',
  templateUrl: './page-images-videos.component.html',
  styleUrls: ['./page-images-videos.component.css']
})
export class PageImagesVideosComponent implements OnInit {

  constructor(private activitedroute:ActivatedRoute,
    private http:HttpClient,
    private sanitizer: DomSanitizer,
    private serviceclient:ServiceClientService,
    private router : Router
  ){}
     @Input() menuimgvideo = {
       showVideo:false,
       showImage:false,
       userRole:false
  
  }
 
  messageExecute!:string;
  messageError!:string;
  
  videos: Video[] = [];
  images:Image[]=[];
  loading: boolean = true;
  error: string = '';
  userInfo!:ClientMou9f;
  id:string ="";
  imageUrls: Map<number, SafeResourceUrl> = new Map();
  videoUrls: Map<number, SafeResourceUrl> = new Map();
  storedUser!:ClientMou9f[];

  // Variables pour la pagination
  currentPage = 0;
  videosPerPage = 6; 


  ngOnInit(): void {
      this.activitedroute.paramMap.subscribe(
        param => {
          this.id = param.get('id') || '';
          if(this.id!=""){
            this.getImages();
            this.getVideos();
          }
        }
      )

      if(this.menuimgvideo.showImage==true || this.menuimgvideo.showVideo==true){
  
        this.getImagescurrentuser();
        this.getVideoscurrentuser();
      }

  }

    
   getVideoUrl(videoId: number): SafeResourceUrl | null {
    return this.videoUrls.get(videoId) || null;
  }

  
    formatFileSize(bytes: number): string {
    if (!bytes) return '0 MB';
    const mb = bytes / (1024 * 1024);
    return mb.toFixed(1) + ' MB';
  }
    // Calcul du nombre total de pages
  get totalPages(): number {
    return Math.ceil(this.videos.length / this.videosPerPage);
  }
    // Page précédente
  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.scrollToTop();
    }
  }
      // Page suivante
  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.scrollToTop();
    }
  }
  getImageUrl(imageId: number): SafeResourceUrl | null {
    return this.imageUrls.get(imageId) || null;
  }
  
    // Getter pour les vidéos paginées
  get paginatedVideos(): any[] {
    const start = this.currentPage * this.videosPerPage;
    const end = start + this.videosPerPage;
    return this.videos.slice(start, end);
  }


    // Scroll en haut de la page
  private scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  getImagescurrentuser(){
    this.http.get<any>(`/api/resource/images`).subscribe(
      {
        next: data => { 
          this.images = data;
          this.images.forEach(image => {
            if (image.bytesImage) {
              // Convertir base64 en Blob
              this.loading=false;
              const blob = this.base64ToBlob(image.bytesImage, image.contentTypeImage);
              const url = URL.createObjectURL(blob);
              const safeUrl = this.sanitizer.bypassSecurityTrustUrl(url);
              this.url=safeUrl;
              this.imageUrls.set(image.id, safeUrl);
            }
            
          });
          console.log("list url ",this.url);

        },
        error: er => {
          console.log("client detail ",er);
        }
      }    
    )
  }


  getVideoscurrentuser(){
    this.http.get<Video[]>(`/api/videos/videos`).subscribe(
        {
        next: data => { 
        this.videos=data;
        this.videos.forEach(video => {
              if (video.contenuVideo) {
                // Convertir base64 en Blob
                this.loading=false;
                const blob = this.base64ToBlob(video.contenuVideo, video.typeVideo);
                const url = URL.createObjectURL(blob);
                const safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
                this.videoUrls.set(video.id, safeUrl);
                // this.url=safeUrl;
              }
              // console.log("list url ",this.url);
            });

          },
          error: er => {
            console.log("client detail ",er);
          }
        })

  }

  getImages(){
    this.http.get<any>(`/open/image/${this.id}`).subscribe(
      {
        next: data => { 
          this.images = data;
          this.images.forEach(image => {
            if (image.bytesImage) {
              // Convertir base64 en Blob
              this.loading=false;
              const blob = this.base64ToBlob(image.bytesImage, image.contentTypeImage);
              const url = URL.createObjectURL(blob);
              const safeUrl = this.sanitizer.bypassSecurityTrustUrl(url);
              this.url=safeUrl;
              this.imageUrls.set(image.id, safeUrl);
            }
            
          });
          console.log("list url ",this.url);

        },
        error: er => {
          console.log("client detail ",er);
        }
      }    
    )
  }

  getVideos(){
    this.http.get<Video[]>(`/open/video/${this.id}`).subscribe(
        {
        next: data => { 
        this.videos=data;
        this.videos.forEach(video => {
              if (video.contenuVideo) {
                // Convertir base64 en Blob
                this.loading=false;
                const blob = this.base64ToBlob(video.contenuVideo, video.typeVideo);
                const url = URL.createObjectURL(blob);
                const safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
                this.videoUrls.set(video.id, safeUrl);
                // this.url=safeUrl;
              }
              // console.log("list url ",this.url);
            });

          },
          error: er => {
            console.log("client detail ",er);
          }
        })

  }

  url:any;
  base64ToBlob(base64: string, contentType: string): Blob {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: contentType });
  }
 
   // Méthode pour retourner à l'accueil
  goToHomePage(): void {
    this.router.navigate(['/home/accueil']);
  
  }

message:string="";
 deleteVideo(id:any){
    this.http.delete<any>(`/api/videos/delete/${id}`).subscribe({
       next: (message)=>{
         console.log(message)
      },
      error: (error)=>{
         console.log("errrror :", error )
      }
    })
 }

  deleteImg(id:number){
    this.http.delete<any>(`/api/resource/delete/${id}`).subscribe({
      next: (message)=>{
          if(message.messageExecute != ''){
            this.messageExecute = message.messageExecute;
          }
          if(message.messageError != ''){
            this.messageError = message.messageError;
          };
      },
      error: (error)=>{
         console.log("errrror :", error )
      }
    })

 }
 

 

}






