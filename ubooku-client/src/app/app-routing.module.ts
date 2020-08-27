import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedDirective } from './shared.directive';

const routes: Routes = [];

@NgModule({
   imports: [
      RouterModule.forRoot(routes)
   ],
   exports: [
      RouterModule
   ],
   declarations: [
      SharedDirective
   ]
})
export class AppRoutingModule { }
