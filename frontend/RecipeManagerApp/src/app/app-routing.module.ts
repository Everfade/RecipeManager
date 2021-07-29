import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RecipeComponent} from "./recipe/recipe.component";
import {SuggestionsComponent} from "./suggestions/suggestions.component";
import {RecipeDetailComponent} from "./recipe-detail/recipe-detail.component";
import {TagComponent} from "./tag/tag.component";
import { FormsModule } from '@angular/forms';
import {StartComponent} from "./start/start.component";
const routes: Routes = [ {path: 'recipes', component: RecipeComponent},
  { path: 'suggestions', component: SuggestionsComponent},
  { path: 'tags', component: TagComponent},
  { path: 'home', component: StartComponent},
  {path:'recipes/:id',component: RecipeDetailComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
