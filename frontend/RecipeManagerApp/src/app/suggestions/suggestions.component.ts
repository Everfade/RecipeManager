import { Component, OnInit } from '@angular/core';
import {Tag} from "../entity/Tag";
import {RecipeService} from "../service/RecipeService";
import {ActivatedRoute, Router} from "@angular/router";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {TagService} from "../service/TagService";
import {Recipe} from "../entity/Recipe";

@Component({
  selector: 'app-suggestions',
  templateUrl: './suggestions.component.html',
  styleUrls: ['./suggestions.component.scss']
})
export class SuggestionsComponent implements OnInit {
  recipeService:RecipeService;
  tagsService:TagService;
  public tags:Tag[]
  public error:boolean=false;
  public displayAlert: boolean=false;
  public errorMessage: string="";
  public alertMessage: string="";
  constructor(private rs:RecipeService,private ts:RecipeService ,private router: Router,
              private route: ActivatedRoute,private modalService:NgbModal) {

    this.recipeService=rs;
  }

  ngOnInit(): void {
  }

  public  getTags():void{
    this.tagsService.getTags().subscribe(
      (response:Tag[])=>{
        this.tags= response;
        console.log(response);
      }

    )}

}
