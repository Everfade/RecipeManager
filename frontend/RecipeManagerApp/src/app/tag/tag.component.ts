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
  displayAlert: boolean=false;
  displayAlertModal:boolean=false;
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
    this.displayAlert=false;
  }

  findFittingRecipe(content: any) {




      let searchTags:String[]=[];
       for(let i=0;i<this.selectedTags.length;i++){
         let index=this.tags.findIndex(
           (item) => item.id==this.selectedTags[i]);
         if(index>0&& index<this.tags.length) {
           searchTags.push(this.tags[index].name)
         }
       }
       let s=searchTags.toString()

       console.log("data: "+searchTags)
        this.recipeService.getRecipesFiltered(searchTags).subscribe(data => {
          this.recipes = data;
          this.showTagTable = false;
          this.showRecipeTable = true;

      })



  }


  save(content: any) {
    if(this.modalContent.name.includes(",")){
      this.showAlert("Name can't contain symbol:,");
      return;
    }
    if(this.modalContent.name==""){
      this.showAlert("Name can't be empty")
      return;
    }
    this.tagService.postTag(this.modalContent).subscribe(
      resp => {
      }, (error: HttpErrorResponse) => {
        this.defaultServiceErrorHandling(error);
        setTimeout(() => {
          this.error = false;
        }, 5000);
      }, () => {
        this.showAlert("Tag added")
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

  private showAlert(message: string) {
    if(!this.displayAlert){
      this.displayAlertModal=true;
    }

    this.alertMessage=message;
    setTimeout(()=>{
      this.displayAlertModal=false;
      this.displayAlert=false;
      this.alertMessage="";

    },4000)

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
    this.router.navigate(['recipes/' + this.recipes[id].id], {state: {data: {Number: this.recipes[id].id}}});
  }

  navigateBack() {
    this.showRecipeTable=false;
    this.showTagTable=true;
  }

  editTag(id,content:any) {
    console.log(id)
    this.modalContent=this.tags[id];
    this.modalService.open(content);

  }

  saveTagEdit(content:any) {
    if(this.modalContent.name.includes(",")){
      this.showAlert("Name can't contain symbol:,");
      return;
    }
    if(this.modalContent.name==""){
      this.showAlert("Name can't be empty")
      return;
    }
    this.tagService.updateTag(this.modalContent).subscribe(()=>{
      this.displayAlert=true;

      this.modalService.dismissAll();
      this.getAllTags();
      this.modalContent.name="";
      this.modalContent.color="";
      this.showAlert("tag updated");
    },(error:HttpErrorResponse)=> {
      this.displayAlertModal=true;
      this.showAlert(error.message);

      setTimeout(()=>{
        this.displayAlertModal=false;
      },5000)

    });

  }
}
