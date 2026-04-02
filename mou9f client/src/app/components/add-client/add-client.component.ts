import { Component , OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';



@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.css']
})
export class AddClientComponent implements OnInit {


  profileForm!: FormGroup;
  submitted = false;
  successMessage = '';

  // Liste des métiers pour le champ select
  professions: string[] = [
    'Développeur',
    'Médecin',
    'Enseignant',
    'Avocat',
    'Ingénieur',
    'Architecte',
    'Commerçant',
    'Artisan',
    'Consultant',
    'Autre'
  ];

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.profileForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      prenom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      numTelephone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]],
      adresse: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(200)]],
      metier: ['', Validators.required]
    });
  }

  // Getter pour faciliter l'accès aux champs du formulaire
  get f() { return this.profileForm.controls; }

  onSubmit(): void {
    this.submitted = true;
    this.successMessage = '';

    // Arrêter si le formulaire est invalide
    if (this.profileForm.invalid) {
      return;
    }

    // Traitement des données du formulaire
    const formData = this.profileForm.value;
    console.log('Données du formulaire:', formData);
    
    // Ici, vous pouvez envoyer les données à votre API
    this.saveProfile(formData);
  }

  saveProfile(profileData: any): void {
    // Simuler un appel API
    setTimeout(() => {
      this.successMessage = 'Profil enregistré avec succès !';
      this.submitted = false;
      
      // Optionnel : réinitialiser le formulaire
      // this.profileForm.reset();
    }, 1000);
  }

  resetForm(): void {
    this.submitted = false;
    this.successMessage = '';
    this.profileForm.reset();
  }
}


