import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { EditEventComponent } from './components/edit-event/edit-event.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { ErrorPageComponent } from './components/error-page/error-page.component';
import { EventGraphsComponent } from './components/event-graphs/event-graphs.component';

import { EventComponent } from './components/event/event.component';
import { IndexComponent } from './components/index/index.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { SingleEventComponent } from './components/single-event/single-event.component';
import { UserComponent } from './components/user/user.component';

const routes: Routes = [
  {path: 'events', component: EventComponent},
  {path: 'admin', component: AdminComponent},
  {path: '', component: IndexComponent},
  {path: 'reservation', component: ReservationComponent},
  {path: 'profile', component: UserComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component:RegisterComponent},
  {path: 'error',component:ErrorPageComponent},
  {path:'edit-profile',component:EditProfileComponent},
  {path: 'edit-event/:id', component:EditEventComponent},
  {path:'see-event/:id',component:SingleEventComponent},
  {path: 'graph-event/:id', component:EventGraphsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routing = RouterModule.forRoot(routes);