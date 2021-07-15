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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {ScrollingModule} from "@angular/cdk/scrolling";
import {MatIconModule} from "@angular/material/icon";
import {MatListModule} from "@angular/material/list";
import {MatDividerModule} from "@angular/material/divider";
import {MatButtonModule} from "@angular/material/button";
import {MatCard, MatCardContent, MatCardModule} from "@angular/material/card";
import {ColorPickerModule} from "ngx-color-picker";
import {MatChipsModule} from "@angular/material/chips";


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    TagComponent,
    SuggestionsComponent,
    RecipeDetailComponent,
    RecipeComponent,
    StringhandlerComponent,
    StringListInputDirective,





  ],
  imports: [
    BrowserModule,CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,ReactiveFormsModule, BrowserAnimationsModule,
    ScrollingModule,
    MatToolbarModule,
    MatListModule,
    MatDividerModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatProgressSpinnerModule,MatCardModule,
    ColorPickerModule,
    MatChipsModule


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {




}
