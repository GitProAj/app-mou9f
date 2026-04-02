import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { VideoServiceService } from 'src/app/service/video-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.css']
})
export class UploadVideoComponent implements OnInit {

  // Formulaire réactif Angular
  uploadForm!: FormGroup;
  
  // Fichier sélectionné
  selectedFile: File | null = null;
  
  // État de l'upload
  isUploading: boolean = false;
  uploadSuccess: boolean = false;
  errorMessage: string = '';
  
  // Progression simulée (optionnel)
  uploadProgress: number = 0;
  private progressInterval: any;

  constructor(
    private fb: FormBuilder,        // Pour construire le formulaire
    private videoService: VideoServiceService,  // Service pour l'upload
    private router: Router           // Pour la navigation
  ) {}

  ngOnInit(): void {
    // Initialisation du formulaire avec validation
    this.uploadForm = this.fb.group({
      titre: ['', [
        Validators.required,           // Champ requis
        Validators.minLength(3),       // Minimum 3 caractères
        Validators.maxLength(100)       // Maximum 100 caractères
      ]],
      description: ['', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(500)
      ]]
    });
  }

  /**
   * Getter pratique pour accéder aux champs du formulaire dans le template
   */
  get titre(): AbstractControl | null {
    return this.uploadForm.get('titre');
  }

  get description(): AbstractControl | null {
    return this.uploadForm.get('description');
  }

  /**
   * Appelé quand l'utilisateur sélectionne un fichier
   * @param event L'événement de sélection de fichier
   */
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      
      // VALIDATION 1: Type de fichier
      const validTypes = [
        'video/mp4', 
        'video/mpeg', 
        'video/quicktime', 
        'video/x-msvideo',
        'video/webm',
        'video/ogg'
      ];
      
      if (!validTypes.includes(file.type)) {
        this.errorMessage = 'Format de vidéo non supporté. Utilisez MP4, MPEG, MOV, AVI, WebM ou OGG.';
        this.selectedFile = null;
        input.value = ''; // Reset l'input file
        return;
      }

      // VALIDATION 2: Taille du fichier (max 500MB)
      const maxSize = 500 * 1024 * 1024; // 500MB en bytes
      if (file.size > maxSize) {
        const sizeInMB = (file.size / (1024 * 1024)).toFixed(2);
        this.errorMessage = `La vidéo ne doit pas dépasser 500MB (taille actuelle: ${sizeInMB}MB).`;
        this.selectedFile = null;
        input.value = '';
        return;
      }

      // VALIDATION 3: Taille minimale (optionnel)
      const minSize = 1024; // 1KB
      if (file.size < minSize) {
        this.errorMessage = 'Le fichier est trop petit.';
        this.selectedFile = null;
        input.value = '';
        return;
      }

      // Toutes les validations sont passées
      this.selectedFile = file;
      this.errorMessage = '';
      
      // Afficher des infos sur le fichier (optionnel)
      console.log('Fichier sélectionné:', {
        nom: file.name,
        type: file.type,
        taille: `${(file.size / (1024 * 1024)).toFixed(2)} MB`,
        file:  this.selectedFile
      });
    }
  }

  /**
   * Soumission du formulaire
   */
  onSubmit(): void {
    // Vérifier que le formulaire est valide ET qu'un fichier est sélectionné
    if (this.uploadForm.valid && this.selectedFile) {
      
      this.isUploading = true;
      this.uploadProgress = 0;
      
      // Récupérer les valeurs du formulaire
      const { titre, description } = this.uploadForm.value;
      
      // Simuler une progression (optionnel, pour UX)
      this.simulateProgress();
      
      // Appel au service pour l'upload
      this.videoService.uploadVideo(titre, description, this.selectedFile)
        .subscribe({
          next: (response) => {
            // Succès
            this.isUploading = false;
            this.uploadSuccess = true;
            this.clearProgressSimulation();
            console.log("errooooooooor ",response.message)
            
            // Rediriger vers la liste après 2 secondes
            setTimeout(() => {
              this.router.navigate(['/videos']);
            }, 2000);
          },
          error: (error) => {
            // Erreur
            this.isUploading = false;
            this.uploadSuccess = false;
            this.clearProgressSimulation();
            this.errorMessage = 'Erreur lors de l\'upload. Veuillez réessayer.';
            console.error('Détails de l\'erreur:', error);
          }
        });
    } else {
      // Marquer tous les champs comme touchés pour afficher les erreurs
      this.uploadForm.markAllAsTouched();
      
      if (!this.selectedFile) {
        this.errorMessage = 'Veuillez sélectionner un fichier vidéo.';
      }
    }
  }

  /**
   * Simule une progression pour améliorer l'UX
   */
  private simulateProgress(): void {
    this.progressInterval = setInterval(() => {
      if (this.uploadProgress < 90) {
        this.uploadProgress += 10;
      } else {
        clearInterval(this.progressInterval);
      }
    }, 500);
  }

  /**
   * Arrête la simulation de progression
   */
  private clearProgressSimulation(): void {
    if (this.progressInterval) {
      clearInterval(this.progressInterval);
    }
  }

  /**
   * Annule la sélection du fichier
   */
  cancelSelection(): void {
    this.selectedFile = null;
    this.errorMessage = '';
    // Reset l'input file via son ID
    const fileInput = document.getElementById('fichier') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }

  /**
   * Formate la taille du fichier pour l'affichage
   */
  formatFileSize(size: number): string {
    if (size < 1024) return size + ' B';
    if (size < 1024 * 1024) return (size / 1024).toFixed(2) + ' KB';
    if (size < 1024 * 1024 * 1024) return (size / (1024 * 1024)).toFixed(2) + ' MB';
    return (size / (1024 * 1024 * 1024)).toFixed(2) + ' GB';
  }

  /**
   * Vérifie si le formulaire peut être soumis
   */
  canSubmit(): boolean {
    return this.uploadForm.valid && !!this.selectedFile && !this.isUploading;
  }
}

