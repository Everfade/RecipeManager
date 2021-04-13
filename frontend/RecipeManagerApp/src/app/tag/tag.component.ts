import { Component, OnInit } from '@angular/core';
import {RecipeService} from "../service/RecipeService";
import {Recipe} from "../entity/Recipe";
import {ActivatedRoute, Router} from "@angular/router";
import {TagService} from "../service/TagService";
import {Tag} from "../entity/Tag";


@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.scss']
})

export class TagComponent implements OnInit {
  public tags:Tag[];
  tagService:TagService;
  public  recipes:Recipe[]=[];


  constructor(private rs:TagService  ,private router: Router,
              private route: ActivatedRoute) {

    this.tagService=rs;
  }


  ngOnInit(): void {
    this.getAllTags();
  }

  public  getAllTags():void{
    this.tagService.getTags().subscribe((response)=>{
        this.tags= response;
        console.log(response);
      }

    )}

  openTagAdd() {

  }

  openTagEdit(id: number) {

  }

  removeTag(id: number) {

  }
}
