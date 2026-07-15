import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadVideoComponent } from './components/user/components/upload-video/upload-video.component';
import { PageClientComponent } from './components/user/components/accueil/page-client/page-client.component';
import { AccueilComponent } from './components/client/accueil/accueil.component';
import { LogoutComponent } from './components/logout/logout.component';
import { SidebarComponent } from './components/user/components/accueil/sidebar/sidebar.component';
import { AddClientComponent } from './components/signup/add-client/add-client.component';
import { AcheteComponent } from './components/signup/achete/achete.component';
import { PageAdminComponent } from './components/admin/page-admin/page-admin.component';
import { DetailclientComponent } from './components/client/detailclient/detailclient.component';
import { UploadImageComponent } from './components/user/components/upload-image/upload-image.component';
import { PageImagesVideosComponent } from './components/client/page-images-videos/page-images-videos.component';
import { AppComponent } from './app.component';
import { roleGuard } from './components/auth/guard/user-guard.guard';

const routes: Routes = [
  {
    path:'home',
       children:[
          //  {
            // path: 'login',
            // redirectTo: window.location.href = '/oauth2/authorization/gateway',
            // pathMatch: 'full'
            // },
            {path:'signup',component:AddClientComponent},
            {path :'achete',component:AcheteComponent},
            {path : 'accueil',component:AccueilComponent},
            {path : 'detail/client/:id',component:DetailclientComponent},   
            { 
              path : 'client',component:PageClientComponent,
              canActivate:[roleGuard],
                  data:{
                      roles:['USER']
                  },
            },
            {
              path : "admin",component:PageAdminComponent,
              canActivate:[roleGuard],
                  data:{
                      roles:['USER']
                  },
            },
      ]

  }   

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
