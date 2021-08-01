import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Recipe} from "../entity/Recipe";
import {RecipeService} from "../service/RecipeService";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {TagService} from "../service/TagService";
import {Tag} from "../entity/Tag";
import {HttpErrorResponse} from "@angular/common/http";
import {DomSanitizer, SafeUrl} from '@angular/platform-browser'
@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss']
})
export class RecipeDetailComponent implements OnInit {
  recipeService: RecipeService;
  tagsService: TagService;
  public tags: Tag[] = [];
  public error: boolean = false;
  public displayAlert: boolean = false;
  public errorMessage: string = "";
  public alertMessage: string = "";
  private selectedFile:File;
  public modalContent: Recipe={description: "", id: 0, ingredients: "",
    instructions: [], tags: [], name:"r1",    imageData: "undefined"};
  public recipe: Recipe=this.modalContent;
  id: number;
  public selectedFileName:string="";
  public displayImage:any;

  constructor(private sanitizer:DomSanitizer,private rs: RecipeService, private ts: TagService, private router: Router,
              private route: ActivatedRoute, private modalService: NgbModal) {
    this.recipeService = rs;
    this.tagsService = ts;
  }

  ngOnInit(): void {
    this.id = parseInt(this.route.snapshot.paramMap.get('id'))
    console.log(this.id);
    this.rs.getRecipeBy(this.id).subscribe((data: Recipe) => {

      this.modalContent = data;
      this.recipe = data;
      const contentType = 'image/png';
     // const blob= new Blob([data.imageData],{type:contentType});
      const blob = 'data:image/png;base64,' + data.imageData;
      this.displayImage  = this.sanitizer.bypassSecurityTrustUrl(blob);


    });
    this.getTags();
  };

  openRecipeAdd(content: any) {
    this.modalService.open(content);

  }


  openRecipeEdit(content:any) {
    this.modalService.open(content);

  }

  public getTags(): void {
    this.tagsService.getTags().subscribe(
      (response: Tag[]) => {
        this.tags = response;
        console.log(this.tags);
      }
    )
  }

  remove() {
    if(confirm("Are you sure you want to delete \""+this.recipe.name+"\"recipe?")) {
      console.log("Implement delete functionality here");

      this.rs.deleteRecipes(this.id).subscribe({
        next: data => {
          console.log('Delete successful');

        },
        error: error => {
          return error.message;
          console.error('There was an error!', error);

        }
      });
      this.router.navigate(['recipes/'], {state: {data: {string: "Recipe has been deleted."}}});
    }
  }




  save(content: any) {
    try {
      let temp= this.modalContent.instructions;
      this.modalContent.instructions.forEach(function (s){
        (<String>s).replace('•','')});
      console.log(this.modalContent)
      this.recipeService.updateRecipe(this.modalContent).subscribe(
        resp => {
        }, (error: HttpErrorResponse) => {
          this.modalContent.instructions=temp;
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

  private showAlert(recipeAdded: string) {
    this.displayAlert = true;
    this.alertMessage = recipeAdded;
  }

  hideAlert($event: MouseEvent) {
    this.displayAlert = false;

  }

  onFileChanged(event) {
    const file = event.target.files[0]
    this.selectedFileName=file.name
    this.selectedFile=file;
  }

  onUpload() {
    let data=""
    this.selectedFileName="";
    const reader: FileReader = new FileReader();
    reader.readAsDataURL(this.selectedFile);
    reader.onload = (): string => {
      const base64String: string = (reader.result as string).match(
        /.+;base64,(.+)/
      )[1];
      this.recipe.imageData=base64String;
      this.rs.addRecipeImage(this.recipe);
     return base64String;
    };






  }


}
