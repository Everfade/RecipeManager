import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { RecipeComponent } from './recipe/recipe.component';
import {HttpClientModule} from "@angular/common/http";
import {RecipeService} from "./service/RecipeService";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SuggestionsComponent } from './suggestions/suggestions.component';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component';
import { TagComponent } from './tag/tag.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RecipeComponent,
    SuggestionsComponent,
    RecipeDetailComponent,
    TagComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,HttpClientModule, NgbModule

  ],
  providers: [RecipeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
