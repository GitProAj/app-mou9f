import { Component,OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UpdateStatus } from '../model/UpdateStatus';
 
export interface Client {
  firstname: string;
  lastname: string;
  username: string;
  ville_activite: string;
  lieut_activite: string;
  phone: number;
  activite: string;
  enabled:boolean;
  trialPeriod:boolean;
}



@Component({
  selector: 'app-page-admin',
  templateUrl: './page-admin.component.html',
  styleUrls: ['./page-admin.component.css']
})
export class PageAdminComponent implements OnInit {
  clients: Client[] = [];
  loading: boolean = false;
  error: string = '';



  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loading = true;
    
    this.http.get<Client[]>('/open/getAllClient')
      .subscribe({
        next: (data) => {
          this.clients = data;
          console.log(data)
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Erreur lors du chargement des clients. Veuillez réessayer.';
          this.loading = false;
          console.error('Erreur:', err);
        }
      });
  } 

  updateStatu(status:boolean,username:string){
    const updateStatus = new UpdateStatus(username,!status);
    console.log("username",updateStatus.username);
    console.log("status",updateStatus.status)


    this.http.put<any>('/open/updateStatus',updateStatus).subscribe({
      next: (req)=>{
        this.clients.forEach(x=>{
           if(x.username==username){
            x.enabled=req;
           }
        })
      },
      error: er=>{}
    })

  }

}
