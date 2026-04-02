import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UploadImageComponent } from './components/image/upload-image/upload-image.component';
import { UploadVideoComponent } from './components/video/upload-video/upload-video.component';
import { PageAdminComponent } from './components/admin/page-admin/page-admin.component';
import { PageClientComponent } from './components/client/page-client/page-client.component';
import { AccueilComponent } from './components/accueil/accueil.component';
import { AddClientComponent } from './components/add-client/add-client.component';
import { LogoutComponent } from './components/logout/logout.component';
import { SidebarComponent } from './components/client/sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
    SidebarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
