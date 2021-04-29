import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GraphsTableComponent } from './components/graphs-table/graphs-table.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { ErrorPageComponent } from './components/error-page/error-page.component';
import { AdminComponent } from './components/admin/admin.component';
import { EditEventComponent } from './components/edit-event/edit-event.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { EventCreatorComponent } from './components/event-creator/event-creator.component';
import { FooterComponent } from './components/footer/footer.component';
import { EventGraphsComponent } from './components/event-graphs/event-graphs.component';
import { RegisterComponent } from './components/register/register.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { SingleEventComponent } from './components/single-event/single-event.component';
import { UserComponent } from './components/user/user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { EventComponent } from './components/event/event.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    AppComponent,
    GraphsTableComponent,
    HeaderComponent,
    LoginComponent,
    ErrorPageComponent,
    AdminComponent,
    EditEventComponent,
    EditProfileComponent,
    EventCreatorComponent,
    FooterComponent,
    EventGraphsComponent,
    RegisterComponent,
    ReservationComponent,
    SingleEventComponent,
    UserComponent,
    EventComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    RouterModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
