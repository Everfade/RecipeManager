import { Component, OnInit } from '@angular/core';
import {RecipeService} from "../service/RecipeService";
import {Recipe} from "../entity/Recipe";
import {HttpErrorResponse} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss']
})
export class RecipeComponent implements OnInit {
   recipeService:RecipeService;
  public  recipes:Recipe[]=[];


  constructor(private rs:RecipeService  ,private router: Router,
              private route: ActivatedRoute) {

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
    this.router.navigate(['recipes/' + this.recipes[id].id], {state: {data: {Number: this.recipes[id].id}}});
  }

  openRecipeEdit(id: number) {

  }

  openRecipeAdd( ) {

  }
}
