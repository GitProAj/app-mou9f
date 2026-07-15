import { Component , OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SignupService } from '../../user/service/signup/signup.service';
import {  Router } from '@angular/router';
import { user } from '../../user/model/user';
import { client } from '../../user/model/client';
import { addClientWithUser } from '../../user/model/addClientWithUser';



@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.css']
})
export class AddClientComponent implements OnInit {

  showPassword:boolean=true;
  profileForm!: FormGroup;
  submitted = false;
  successMessage = '';
  messageeror='';

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

  constructor(private formBuilder: FormBuilder , 
              private signupservice:SignupService,
              private router:Router            
            ) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.profileForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      prenom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      username: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      password: ['', [Validators.required, Validators.pattern('^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9]{5,10}$')]],
      Telephone: ['', [Validators.required, Validators.pattern('^0[0-9]{9}$')]],
      ville: ['', Validators.required],
      lieut: ['', Validators.required],
      metier: ['', Validators.required]
    });
  }
  dataclient():any{
    const data = new client(
        this.profileForm.value.nom,
        this.profileForm.value.prenom,       
        this.profileForm.value.username,
        this.profileForm.value.password,
        this.profileForm.value.ville,
        this.profileForm.value.lieut,
        this.profileForm.value.Telephone,
        this.profileForm.value.metier,
        ["USER"]
      )
    return data;
  }
  acount():any{
     var userrussource = new user(
        this.profileForm.value.username,
        this.profileForm.value.password,
        ["USER"]
      )
    return userrussource;
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
    
     const data = this.dataclient();
   

        this.signupservice.addClient(data).subscribe(
      {
        next :(response)=>{
          if(response.messageExecute !=""){
            console.log("eroor   ",response.messageExecute)
            this.successMessage =response.messageExecute;
            return
          }
            if(response.messageError !=""){
            console.log("eroor   ",response.messageError)
            this.messageeror = response.messageError;
            return
          }
        },
        error : (err) =>{
            console.log("from add error is : ", err)
        }
      })
  
    
    

  
    
    // Traitement des données du formulaire
    const formData = this.profileForm.value;
    console.log('Données du formulaire:', formData);
    
    // Ici, vous pouvez envoyer les données à votre API
    // this.saveProfile(formData);
  }

  saveProfile(profileData: any): void {
    // Simuler un appel API
    setTimeout(() => {
      this.successMessage = 'Profil enregistré avec succès !';
      this.submitted = false;
      
      // Optionnel : réinitialiser le formulaire
      // this.profileForm.reset();
    }, 6000);
  }

  resetForm(): void {
    this.submitted = false;
    this.successMessage = '';
    this.messageeror = '';
    this.profileForm.reset();
  }
  showPass(){
    this.showPassword=!this.showPassword;
  }
}


