import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Recipe} from "../entity/Recipe";
import {RecipeService} from "../service/RecipeService";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {TagService} from "../service/TagService";
import {Tag} from "../entity/Tag";

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss']
})
export class RecipeDetailComponent implements OnInit {
  recipeService: RecipeService;
  tagsService: TagService;
  public tags:Tag[]=[];
  public error: boolean = false;
  public displayAlert: boolean = false;
  public errorMessage: string = "";
  public alertMessage: string = "";

  public recipe: Recipe;
  id: number;

  constructor(private rs: RecipeService, private ts: TagService, private router: Router,
              private route: ActivatedRoute, private modalService: NgbModal) {
    this.recipeService=rs;
    this.tagsService=ts;
  }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'))
    console.log(this.id);
    this.rs.getRecipeBy(this.id).subscribe((data:Recipe) =>{
    console.log(data)
      this.recipe = data;
  });
    this.getTags();
  };



  openRecipeEdit() {

  }
  public  getTags():void{
    this.tagsService.getTags().subscribe(
      (response:Tag[])=>{
        this.tags= response;
        console.log(this.tags);
      }

    )}
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
