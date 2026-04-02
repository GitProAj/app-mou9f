import { Component , OnInit, ViewChild, ElementRef  } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UploadImageService } from 'src/app/service/upload-image.service';

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent implements OnInit {

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  // Formulaire
  uploadForm!: FormGroup;
  
  // États de l'image
  selectedImage: File | null = null;
  previewUrl: string | null = null;
  imageError: string | null = null;
  
  // États de l'upload
  isUploading = false;
  uploadSuccess = false;
  uploadProgress = 0;
  errorMessage: string | null = null;
  
  // Drag & Drop
  isDragover = false;

  // Constantes
  readonly MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
  readonly ALLOWED_TYPES = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
  readonly ALLOWED_EXTENSIONS = ['jpg', 'jpeg', 'png', 'gif', 'webp'];

  constructor(
    private fb: FormBuilder,
    private imageService: UploadImageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  /**
   * Initialisation du formulaire
   */
  private initForm(): void {
    this.uploadForm = this.fb.group({
      titre: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(100)
      ]],
      description: ['', Validators.maxLength(500)],
      isPublic: [true],
      category: ['']
    });
  }

  /**
   * Getters
   */
  get titre() {
    return this.uploadForm.get('titre');
  }

  get description() {
    return this.uploadForm.get('description');
  }

  /**
   * Vérifie si le formulaire peut être soumis
   */
  canSubmit(): boolean {
    return this.uploadForm.valid && 
           !!this.selectedImage && 
           !this.isUploading && 
           !this.uploadSuccess &&
           !this.imageError;
  }

  /**
   * Gère la sélection de fichier
   */
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    
    if (input.files && input.files.length > 0) {
      this.processFile(input.files[0]);
    }
  }

  /**
   * Gère le drag over
   */
  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragover = true;
  }

  /**
   * Gère le drag leave
   */
  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragover = false;
  }

  /**
   * Gère le drop de fichier
   */
  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragover = false;

    if (event.dataTransfer?.files && event.dataTransfer.files.length > 0) {
      this.processFile(event.dataTransfer.files[0]);
    }
  }

  /**
   * Traite le fichier sélectionné
   */
  private processFile(file: File): void {
    // Validation
    const validationError = this.validateImage(file);
    
    if (validationError) {
      this.imageError = validationError;
      this.removeImage();
      return;
    }

    // Nettoyer les erreurs
    this.imageError = null;
    this.selectedImage = file;

    // Créer l'aperçu
    this.createPreview(file);
  }

  /**
   * Valide l'image
   */
  private validateImage(file: File): string | null {
    // Vérification de la taille
    if (file.size > this.MAX_FILE_SIZE) {
      return `L'image est trop volumineuse (max: ${this.MAX_FILE_SIZE / (1024 * 1024)}MB)`;
    }

    // Vérification du type MIME
    if (!this.ALLOWED_TYPES.includes(file.type)) {
      return `Format non supporté. Utilisez: ${this.ALLOWED_TYPES.join(', ')}`;
    }

    // Vérification de l'extension
    const extension = file.name.split('.').pop()?.toLowerCase();
    if (!extension || !this.ALLOWED_EXTENSIONS.includes(extension)) {
      return `Extension non supportée`;
    }

    return null;
  }

  /**
   * Crée un aperçu de l'image
   */
  private createPreview(file: File): void {
    const reader = new FileReader();
    
    reader.onload = (e: ProgressEvent<FileReader>) => {
      this.previewUrl = e.target?.result as string;
    };

    reader.onerror = () => {
      this.imageError = 'Erreur lors de la lecture du fichier';
      this.removeImage();
    };

    reader.readAsDataURL(file);
  }

  /**
   * Supprime l'image sélectionnée
   */
  removeImage(): void {
    this.selectedImage = null;
    this.previewUrl = null;
    
    if (this.previewUrl) {
      URL.revokeObjectURL(this.previewUrl);
      this.previewUrl = null;
    }
    
    this.resetFileInput();
  }

  /**
   * Réinitialise l'input file
   */
  private resetFileInput(): void {
    if (this.fileInput && this.fileInput.nativeElement) {
      this.fileInput.nativeElement.value = '';
    }
  }

  /**
   * Formate la taille du fichier
   */
  formatFileSize(bytes: number): string {
    if (bytes === 0) return '0 Bytes';
    
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
  }

  /**
   * Annule l'upload
   */
  cancelUpload(): void {
    if (this.isUploading) {
      // TODO: Annuler la requête en cours
    }
    
    this.uploadForm.reset();
    this.removeImage();
    this.isUploading = false;
    this.uploadSuccess = false;
    this.uploadProgress = 0;
    this.errorMessage = null;
  }

  /**
   * Soumission du formulaire
   */
  onSubmit(): void {
    if (!this.canSubmit()) {
      return;
    }

    // Marquer tous les champs comme touchés
    this.uploadForm.markAllAsTouched();

    // Réinitialiser les états
    this.errorMessage = null;
    this.isUploading = true;
    this.uploadSuccess = false;
    this.uploadProgress = 0;

    // Créer le FormData
    const formData = new FormData();
    formData.append('titre', this.uploadForm.get('titre')?.value);
    formData.append('description', this.uploadForm.get('description')?.value || '');
    formData.append('isPublic', this.uploadForm.get('isPublic')?.value);
    formData.append('category', this.uploadForm.get('category')?.value || '');
    formData.append('image', this.selectedImage as File);

    // Appel au service
    this.imageService.uploadImage(formData).subscribe({
      next: (event: any) => {
        if (event.status === 'progress') {
          this.uploadProgress = event.progress;
        } else if (event.status === 'completed') {
          this.uploadProgress = 100;
          this.uploadSuccess = true;
          this.isUploading = false;
          
          // Redirection après 2 secondes
          setTimeout(() => {
            this.router.navigate(['/images']);
          }, 2000);
        }
      },
      error: (error) => {
        console.error('Erreur upload:', error);
        this.errorMessage = error.message || 'Erreur lors de l\'upload';
        this.isUploading = false;
        this.uploadProgress = 0;
      }
    });
  }
}


