import { Component, OnInit } from '@angular/core';
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
  public tags:Tag[]=[];
  tagService:TagService;
  public  recipes:Recipe[]=[];
  displayAlert: boolean;
  alertMessage:string;
  modalContent:Tag={
    color: "", id: 0,
    name:""
  }
  private error: boolean;



  constructor(private ts:TagService  ,private router: Router,
              private route: ActivatedRoute,  private modalService: NgbModal) {

    this.tagService=ts;
  }


  ngOnInit(): void {
    this.getAllTags();
  }

  public  getAllTags():void{
    this.tagService.getTags().subscribe((response:Tag[])=>{
        this.tags= response;
        console.log(response);
      }

    )}

  openTagAdd(content: any) {
    this.modalService.open(content);
  }

  openTagEdit(id: number) {

  }

  removeTag(id: number) {

  }

  hideAlert($event: MouseEvent) {

  }

  findFittingRecipe(content: any) {

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

  delete(id:number) {
    console.log(id);
    this.tagService.deleteTag(this.tags[id].id).subscribe();
    this.tags.splice(id);

    this.getAllTags();
  }

}
