import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GraphsTableComponent } from './graphs-table/graphs-table.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { AdminComponent } from './admin/admin.component';
import { EditEventComponent } from './edit-event/edit-event.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { EventCreatorComponent } from './event-creator/event-creator.component';
import { FooterComponent } from './footer/footer.component';
import { EventGraphsComponent } from './event-graphs/event-graphs.component';
import { RegisterComponent } from './register/register.component';
import { ReservationComponent } from './reservation/reservation.component';
import { SingleEventComponent } from './single-event/single-event.component';
import { UserComponent } from './user/user.component';

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
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
