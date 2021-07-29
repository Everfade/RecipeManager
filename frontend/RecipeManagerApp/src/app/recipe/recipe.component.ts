import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {RecipeService} from "../service/RecipeService";
import {Recipe} from "../entity/Recipe";
import {HttpErrorResponse} from "@angular/common/http";

import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

;
import {TagService} from "../service/TagService";
import {Tag} from "../entity/Tag";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss']
})
export class RecipeComponent implements OnInit {
  recipeService: RecipeService;
  tagService: TagService;
  public recipes: Recipe[] = [];
  public tags: Tag[] = [];
  public selectedTags: Number[] = [];
  public error: boolean = false;
  public displayAlert: boolean = false;
  public errorMessage: string = "";
  public alertMessage: string = "";
  dataSource:MatTableDataSource<Recipe>= new MatTableDataSource(this.recipes);
  public modalContent: Recipe = {
    id: null,
    name: "",
    description: "",
    ingredients: "",
    instructions: [],
    tags: []
  };
  public selectedRecipe = this.modalContent;
  tagModal: any;
  public displayAlertModal: boolean = false;


  constructor(private rs: RecipeService, private  ts: TagService, private router: Router,
              private route: ActivatedRoute, private modalService: NgbModal) {

    this.recipeService = rs;
    this.tagService = ts;
  }

  ngOnInit(): void {
    this.route.data.subscribe((data) => {
      console.log(data);
    })
    this.getRecipes();
    this.getAllTags();



  }

  public getAllTags(): void {
    this.tagService.getTags().subscribe((data) => {
        this.tags = data;
        console.log(data);
      }
    )
  }

  public getRecipes(): void {
    this.recipeService.getRecipes().subscribe(
      (response: Recipe[]) => {
        this.recipes = response;
        this.dataSource  = new MatTableDataSource(this.recipes);
        console.log(response);
      }
    )
  }

  openRecipeDetail(id: number) {
    this.router.navigate(['recipes/' + this.recipes[id].id], {state: {data: {Number: this.recipes[id].id}}});
  }


  save(content: any) {
    if (this.modalContent.name.includes(",")) {
      this.showAlert("Name can't contain symbol:,");
      return;
    }
    if (this.modalContent.name == "") {
      this.showAlert("Name can't be empty")
      return;
    }
    try {
      let temp = this.modalContent.instructions;
      this.modalContent.instructions.forEach(function (s) {
        (<String>s).replace('•', '')
      });
      console.log(this.modalContent)
      this.recipeService.addRecipes(this.modalContent).subscribe(
        resp => {
        }, (error: HttpErrorResponse) => {
          this.modalContent.instructions = temp;
          this.defaultServiceErrorHandling(error);
          setTimeout(() => {
            this.error = false;
          }, 5000);
        }, () => {
          this.recipes.push(this.modalContent);
          this.getRecipes();
          this.showAlert("Recipe added")
          setTimeout(() => {
            this.displayAlert = false;
          }, 5000);
        }
      );
      this.modalService.dismissAll();

    } catch (E) {
      this.errorMessage = E.toString();
      this.showAlert(this.errorMessage);
    }
  }


  private defaultServiceErrorHandling(error: HttpErrorResponse) {
    console.log(error);
    return;
    this.error = true;
    if (error == null) {
      this.errorMessage = "No error Message available";
      return
    }
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }


  private showAlert(message: string) {
    if (!this.displayAlert) {
      this.displayAlertModal = true;
    }

    this.alertMessage = message;
    setTimeout(() => {
      this.displayAlertModal = false;
      this.displayAlert = false;
      this.alertMessage = "";

    }, 4000)

  }

  hideAlert($event: MouseEvent) {
    this.displayAlert = false;

  }

  openTagDialog(content: any, id: number) {
    this.selectedRecipe = this.recipes[id];

    for (let i = 0; i < this.selectedRecipe.tags.length; i++) {
      //selects indicies of tags that this recipe already has
      this.selectedTags.push(this.tags.findIndex(t => t.id == i));
    }
    this.modalService.open(content);

  }

  compareFunction = (o1: any, o2: any) => o1.id === o2.id;

  saveTags(content: any) {
    this.recipeService.updateRecipe(this.selectedRecipe).subscribe(response => {

      this.recipes[this.recipes.findIndex(item => item.id == this.selectedRecipe.id)].tags = this.selectedRecipe.tags;

    });
    this.modalService.dismissAll();

  }

  openAddRecipeModal(content: any) {
    this.modalService.open(content);

  }

  applyFilter(filterValue: Object) {

    this.dataSource.filter=filterValue.toString().trim().toLowerCase();



  }
}
