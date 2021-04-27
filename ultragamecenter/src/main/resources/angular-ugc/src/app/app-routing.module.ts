import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EventComponent } from './components/event/event.component';

const routes: Routes = [
  {path: 'events', component: EventComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routing = RouterModule.forRoot(routes);