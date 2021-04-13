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
    this.rs.getRecipeBy(this.id).subscribe((data)=>
    this.recipe=data)};

  openRecipeEdit() {

  }

  remove() {

  }

  hideAlert($event: MouseEvent) {

  }
}
