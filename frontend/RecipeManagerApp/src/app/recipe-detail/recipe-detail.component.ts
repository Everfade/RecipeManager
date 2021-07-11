import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Recipe} from "../entity/Recipe";
import {RecipeService} from "../service/RecipeService";

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss']
})
export class RecipeDetailComponent implements OnInit {
  displayAlert:Boolean=false;
  alertMessage:String='';
  recipe: Recipe;
  id:number;
  constructor(  private rs:RecipeService  ,private router: Router,
                private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'))
    console.log(this.id);
    this.rs.getRecipeBy(this.id).subscribe((data)=>
    this.recipe=data)};

  openRecipeEdit() {

  }

  remove() {
    this.rs.deleteRecipes(this.id) .subscribe({
      next: data => {
         console.log( 'Delete successful');

      },
      error: error => {
        return error.message;
        console.error('There was an error!', error);

      }
    });
    this.router.navigate(['recipes/'], {state: {data: {string: "Recipe has been deleted."}}});
  }

  hideAlert($event: MouseEvent) {

  }
}
