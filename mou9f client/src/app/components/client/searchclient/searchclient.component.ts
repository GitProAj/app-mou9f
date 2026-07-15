import { Component, OnInit, HostListener , NgModule} from '@angular/core';
import { Router } from '@angular/router';
import { ClientMou9f } from '../client-mou9f';
import { ServiceClientService } from '../service/service-client.service';

export interface Filtres{
        activite: string;
        ville: string;
        lieu: string;
};

@Component({
  selector: 'app-searchclient',
  templateUrl: './searchclient.component.html',
  styleUrls: ['./searchclient.component.css']
})
export class SearchclientComponent implements OnInit {
    isLoggedIn = false;
    userName = '';
    showUserMenu = false;
    showMobileMenu = false;

    // Listes pour les selects
    activites: string[] = [];
    villes: string[] = [];
    lieux: string[] = [];
    
    // Sélections courantes
    filtres: Filtres = {activite: '',ville: '',lieu: ''};
    
    // Résultats
    clientsFiltres: ClientMou9f[] = [];
    
    // États
    isLoading = false;
    showVilles = false;
    showLieux = false;

  constructor(private clientService: ServiceClientService,
    private router: Router) {}
    
   
        
    ngOnInit(): void {
        const storedUser = localStorage.getItem('clients');
        const villes = localStorage.getItem('villes');
        const lieu = localStorage.getItem('lieux');
        console.log("localstor",storedUser)
        if (storedUser) {
            this.clientsFiltres = JSON.parse(storedUser);
            this.villes = villes ? JSON.parse(villes) : [];
            this.lieux = lieu ? JSON.parse(lieu) : [];
            this.clientsFiltres.forEach(element => {
                if(element.activite){
                    this.filtres.activite  =   element.activite;
                    this.filtres.ville     =   element.ville_activite;
                    this.showLieux=true;
                    if(this.filtres.lieu != ""){
                       this.filtres.lieu   =   element.lieut_activite;
                    }
                    console.log("activite",this.filtres.activite)
                    console.log("ville",this.filtres.ville);
                    console.log("lieu",this.filtres.lieu);
                }            
                return;
            })
        };       
        this.loadActivites();
    }
//   toggleUserMenu() {
//     this.showUserMenu = !this.showUserMenu;
//   }

//   toggleMobileMenu() {
//     this.showMobileMenu = !this.showMobileMenu;
//   }

//   closeMobileMenu() {
//     this.showMobileMenu = false;
//   }


// Close dropdown when clicking outside
//   @HostListener('document:click', ['$event'])
//   onDocumentClick(event: MouseEvent) {
//     if (!event.target) return;
    
//     const target = event.target as HTMLElement;
//     if (!target.closest('.user-menu')) {
//       this.showUserMenu = false;
//     }
//   }



    // Charger toutes les activités
    loadActivites(): void {
        this.isLoading = true;
        this.clientService.getAllActivites().subscribe({
            next: (data) => {
                this.activites = data;
                this.isLoading = false;
            },
            error: (err) => {
                console.error('Erreur chargement activités:', err);
                this.isLoading = false;
            }
        });
    }

    // Quand l'activité change
    onActiviteChange(): void {
        this.filtres.ville = '';
        this.filtres.lieu = '';
        this.villes = [];
        this.lieux = [];
        this.clientsFiltres = [];
        
        if (this.filtres.activite) {
            this.isLoading = true;
            this.clientService.getVillesByActivite(this.filtres.activite).subscribe({
                next: (villes) => {
                    this.villes = villes;
                    localStorage.setItem("villes",JSON.stringify(villes))
                    this.showVilles = true;
                    this.isLoading = false;
                },
                error: (err) => {
                    console.error('Erreur chargement villes:', err);
                    this.isLoading = false;
                }
            });
        } else {
            this.showVilles = false;
            this.showLieux = false;
        }
    }

    // Quand la ville change
    onVilleChange(): void {
        this.filtres.lieu = '';
        this.lieux = [];
        
        if (this.filtres.activite && this.filtres.ville) {
            this.isLoading = true;
            this.clientService.getClientsFilteredByActiviteAndVille(
                this.filtres.activite, 
                this.filtres.ville
            ).subscribe({
                next: (clients) => {
                    clients.forEach(x=>
                       this.lieux.push(x.lieut_activite)
                    );
                    this.clientsFiltres = clients;
                    localStorage.setItem("clients",JSON.stringify(clients));
                    localStorage.setItem("lieux",JSON.stringify(this.lieux));
                    this.showLieux = true;
                    this.isLoading = false;
                },
                error: (err) => {
                    console.error('Erreur chargement lieux:', err);
                    this.isLoading = false;
                }
            });
        } else {
            this.showLieux = false;
        }
    }

    // Quand le lieu change
    onLieuChange(): void {
        this.filterClients(this.filtres.lieu);    

    }

    // Filtrer les clients
    filterClients(lieu:string): void {
        this.isLoading = true;
        const clientsStorege =  localStorage.getItem("clients");
        this.isLoading = false;
        if(clientsStorege){
            this.clientsFiltres = JSON.parse(clientsStorege);
            this.clientsFiltres.filter(x=>x.lieut_activite.includes(lieu))
        }
    }
                  
        //     },
        //     error: (err) => {
        //         console.error('Erreur filtrage clients:', err);
        //         this.isLoading = false;
        //     }
        // });
    

    // Réinitialiser tous les filtres
    resetFilters(): void {
        this.filtres = {
            activite: '',
            ville: '',
            lieu: ''
        };
        this.villes = [];
        this.lieux = [];
        this.clientsFiltres = [];
        this.showVilles = false;
        this.showLieux = false;
        localStorage.removeItem("clients");
        localStorage.removeItem("villes");
        localStorage.removeItem("lieux");
        console.log("this.lieux," , this.filtres.lieu);
        console.log("this.ville," , this.filtres.ville);
        console.log("this.villes.length",this.villes.length);



    }

    // Obtenir le libellé du filtre actuel
    getCurrentFilterLabel(): string {
        let label = '';
        if (this.filtres.activite) label += this.filtres.activite;
        if (this.filtres.ville) label += ' - ' + this.filtres.ville;
        if (this.filtres.lieu) label += ' - ' + this.filtres.lieu;
        return label || 'Tous les clients';
    }

    // Dans votre composant
get hasActiveFilters(): boolean {
    return !!(this.filtres.activite || this.filtres.ville || this.filtres.lieu);
}

clearFilter(filterName: 'activite' | 'ville' | 'lieu'): void {
    switch(filterName) {
        case 'activite':
            this.filtres.activite = '';
            this.onActiviteChange();
            break;
        case 'ville':
            this.filtres.ville = '';
            this.onVilleChange();
            break;
        case 'lieu':
            this.filtres.lieu = '';
            this.onLieuChange();
            break;
    }
}

}


