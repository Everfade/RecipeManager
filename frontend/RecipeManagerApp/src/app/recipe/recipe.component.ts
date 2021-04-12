import { Component, OnInit } from '@angular/core';
import {RecipeService} from "../service/RecipeService";
import {Recipe} from "../entity/Recipe";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss']
})
export class RecipeComponent implements OnInit {
   recipeService:RecipeService;
  public  recipes:Recipe[]=[];


  constructor(private rs:RecipeService) {

    this.recipeService=rs;
  }

  ngOnInit(): void {
    console.log("ree")
    this.getRecipes();
  }
  public  getRecipes():void{
    this.recipeService.getRecipes().subscribe(
    (response:Recipe[])=>{
      this.recipes= response;
      console.log(response);
    }

    )}

  openRecipeDetail(id: number) {

  }

  openRecipeEdit(id: number) {

  }

  openRecipeAdd( ) {

  }
}
