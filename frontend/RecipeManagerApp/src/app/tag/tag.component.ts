import {Component, OnInit} from '@angular/core';
import {RecipeService} from "../service/RecipeService";
import {Recipe} from "../entity/Recipe";
import {ActivatedRoute, Router} from "@angular/router";
import {TagService} from "../service/TagService";
import {Tag} from "../entity/Tag";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpErrorResponse} from "@angular/common/http";


@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.scss']
})

export class TagComponent implements OnInit {
  public showTagTable = true;
  public showRecipeTable = false;
  public tags: Tag[] = [];
  public selectedTags:number[] =[]
  tagService: TagService;
  recipeService: RecipeService;
  public recipes: Recipe[] = [];
  displayAlert: boolean;
  alertMessage: string;
  modalContent: Tag = {
    color: "", id: 0,
    name: ""
  }
  private error: boolean;


  constructor(private ts: TagService, private router: Router,
              private route: ActivatedRoute, private modalService: NgbModal, private  rs: RecipeService) {

    this.tagService = ts;
    this.recipeService = rs;
  }


  ngOnInit(): void {
    this.getAllTags();
  }

  public getAllTags(): void {
    this.tagService.getTags().subscribe((response: Tag[]) => {
        this.tags = response;
        console.log(response);
      }
    )
  }

  openTagAdd(content: any) {
    this.modalService.open(content);
  }
  compareFunction = (o1: any, o2: any)=> o1.id===o2.id;
  openTagEdit(id: number) {

  }

  removeTag(id: number) {

  }

  hideAlert($event: MouseEvent) {

  }

  findFittingRecipe(content: any) {




      let searchTags:String[]=[];
      //for(let i=0;this.selectedTags.length;i++){
        //searchTags.push(this.tags[this.tags.findIndex(
        //  (item) => item.id==this.selectedTags[i] )].name)
  //      }
        this.recipeService.getRecipesFiltered(["Low carb"]).subscribe(data => {
          this.recipes = data;
          this.showTagTable = false;
          this.showRecipeTable = true;

      })



  }


  save(content: any) {
    console.log(this.modalContent)
    this.tagService.postTag(this.modalContent).subscribe(
      resp => {
      }, (error: HttpErrorResponse) => {
        this.defaultServiceErrorHandling(error);
        setTimeout(() => {
          this.error = false;
        }, 5000);
      }, () => {
        this.showAlert("Recipe added")
        this.tags.push(this.modalContent);
        this.getAllTags();

        setTimeout(() => {
          this.displayAlert = false;
        }, 5000);
      }
    );
    this.modalService.dismissAll();
    this.getAllTags();
  }

  private defaultServiceErrorHandling(error: HttpErrorResponse) {

  }

  private showAlert(recipeAdded: string) {

  }

  delete(id: number) {
    console.log(id);
    this.tagService.deleteTag(this.tags[id].id).subscribe(resp => {
      }, (error: HttpErrorResponse) => {
        this.defaultServiceErrorHandling(error);
        setTimeout(() => {
          this.error = false;
        }, 5000);
      }, () => {
        this.showAlert("Tag deleted")
        this.tags.splice(id);

        this.getAllTags();
        this.tags.push(this.modalContent);
        setTimeout(() => {
          this.displayAlert = false;
        }, 5000);
      }
    );

  }

  openRecipeDetail(id: any) {

  }

  navigateBack() {
    this.showRecipeTable=false;
    this.showTagTable=true;
  }
}
