import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import{RecipeDetailComponent} from "./recipe-detail/recipe-detail.component";
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import{TagComponent} from "./tag/tag.component";
import {SuggestionsComponent} from "./suggestions/suggestions.component";
import {CommonModule, DatePipe} from "@angular/common";
import {HeaderComponent} from "./Header/header.component";
import {RecipeComponent} from "./recipe/recipe.component";
import { StringhandlerComponent } from './stringhandler/stringhandler.component';
import { StringListInputDirective } from './string-list-input.directive';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    TagComponent,
    SuggestionsComponent,
    RecipeDetailComponent,
    RecipeComponent,
    StringhandlerComponent,
    StringListInputDirective



  ],
  imports: [
    BrowserModule,CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {




}
