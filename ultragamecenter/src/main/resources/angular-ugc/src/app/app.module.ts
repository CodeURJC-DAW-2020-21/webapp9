import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { AdminComponent } from './admin/admin.component';
import { EditEventComponent } from './edit-event/edit-event.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { EventCreatorComponent } from './event-creator/event-creator.component';
import { FooterComponent } from './footer/footer.component';
import { EventGraphsComponent } from './event-graphs/event-graphs.component';

@NgModule({
  declarations: [
    AppComponent,
    ErrorPageComponent,
    AdminComponent,
    EditEventComponent,
    EditProfileComponent,
    EventCreatorComponent,
    FooterComponent,
    EventGraphsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
