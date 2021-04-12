import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RecipeComponent } from './recipe/recipe.component';
import {HttpClientModule} from "@angular/common/http";
import {RecipeService} from "./service/RecipeService";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RecipeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,HttpClientModule

  ],
  providers: [RecipeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
