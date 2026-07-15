import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptorInterceptor } from './interciotor/auth-interceptor.interceptor';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component'; 
import { UploadImageComponent } from './components/user/components/upload-image/upload-image.component';
import { UploadVideoComponent } from './components/user/components/upload-video/upload-video.component';
import { PageAdminComponent } from './components/admin/page-admin/page-admin.component';
import { PageClientComponent } from './components/user/components/accueil/page-client/page-client.component';
import { AccueilComponent } from './components/client/accueil/accueil.component'; 
import { AddClientComponent } from './components/signup/add-client/add-client.component';
import { LogoutComponent } from './components/logout/logout.component';
import { SidebarComponent } from './components/user/components/accueil/sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AcheteComponent } from './components/signup/achete/achete.component';
import { DetailclientComponent } from './components/client/detailclient/detailclient.component';
import { SearchclientComponent } from './components/client/searchclient/searchclient.component';
import { PageImagesVideosComponent } from './components/client/page-images-videos/page-images-videos.component';

@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    UploadImageComponent,
    PageAdminComponent,
    PageClientComponent,
    AccueilComponent,
    AddClientComponent,
    LogoutComponent,
    SidebarComponent,
    AcheteComponent,
    DetailclientComponent,
    SearchclientComponent,
    PageImagesVideosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule
  ],
  providers: [   {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorInterceptor,
      multi: true  
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
