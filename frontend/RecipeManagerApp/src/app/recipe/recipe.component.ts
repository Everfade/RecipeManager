import {Component,Input, OnInit, TemplateRef} from '@angular/core';
import {RecipeService} from "../service/RecipeService";
import {Recipe} from "../entity/Recipe";
import {HttpErrorResponse} from "@angular/common/http";

import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.scss']
})
export class RecipeComponent implements OnInit {
  recipeService:RecipeService;
  public  recipes:Recipe[]=[];
  public error:boolean=false;
  public displayAlert: boolean=false;
  public errorMessage: string="";
  public alertMessage: string="";
  public  modalContent:Recipe={
    id:null,
    name:"",
    description:"",
    ingredients:"",
    instructions:[]
  };




  constructor(private rs:RecipeService  ,private router: Router,
              private route: ActivatedRoute,private modalService:NgbModal) {

    this.recipeService=rs;
  }

  ngOnInit(): void {
 this.route.data.subscribe((data)=>{
   console.log(data);
 })
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

  openRecipeAdd(content:any) {
    this.modalService.open(content);

  }

  save(content: any) {
    try {
      console.log(this.modalContent)
      this.recipeService.addRecipes(this.modalContent).subscribe(
        resp => {
        }, (error: HttpErrorResponse) => {
          this.defaultServiceErrorHandling(error);
          setTimeout(() => {
            this.error = false;
          }, 5000);
        }, () => {
          this.showAlert("Recipe added")
          setTimeout(() => {
            this.displayAlert = false;
          }, 5000);
        }
      );
      this.modalService.dismissAll();
      this.getRecipes();
    }
    catch (E){
      this.errorMessage=E.toString();
      this.showAlert(this.errorMessage);
    }
  }


  private defaultServiceErrorHandling(error: HttpErrorResponse) {
    console.log(error);
    return;
    this.error = true;
    if(error==null){
      this.errorMessage="No error Message available";
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




  private showAlert(recipeAdded: string) {
    this.displayAlert= true;
    this.alertMessage=recipeAdded;
  }
  hideAlert($event: MouseEvent) {
    this.displayAlert=false;

  }
}
