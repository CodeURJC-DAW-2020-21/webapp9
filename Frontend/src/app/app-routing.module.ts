import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';

import { EventComponent } from './components/event/event.component';

const routes: Routes = [
  {path: 'events', component: EventComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'profile', component: AdminComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routing = RouterModule.forRoot(routes);