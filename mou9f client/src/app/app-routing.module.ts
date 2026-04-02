import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadVideoComponent } from './components/video/upload-video/upload-video.component';
import { PageClientComponent } from './components/client/page-client/page-client.component';
import { AccueilComponent } from './components/accueil/accueil.component';
import { LogoutComponent } from './components/logout/logout.component';
import { SidebarComponent } from './components/client/sidebar/sidebar.component';
import { AddClientComponent } from './components/add-client/add-client.component';

const routes: Routes = [
  // {
  //   path:'',
  //     children:[
  //       {path : 'video',component:UploadVideoComponent},
  //       {path : "client",component:PageClientComponent}
  //     ]
  // }
        {path : 'login',component:SidebarComponent},
        {path : 'sidbar',component:SidebarComponent},
        {path : 'accueil',component:AccueilComponent},
        // {path : 'video',component:UploadVideoComponent},
        {path : "client",component:PageClientComponent},
        {path : "logout",component:LogoutComponent},
        {path : "signup", component:AddClientComponent},
        {path : "dashbord", component:PageClientComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
