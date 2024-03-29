import {Component, Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Recipe} from '../entity/Recipe';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export  class RecipeService{
  private  apiServerUrl=environment.apiBaseUrl;

  constructor(private  httpClient: HttpClient) {

  }
  public  getRecipes(): Observable<Recipe[]>{
    return  this.httpClient.get<Recipe[]>(this.apiServerUrl+'/recipes/all')
  }
  public  getRecipeBy(id:number): Observable<Recipe>{
    return  this.httpClient.get<Recipe>(this.apiServerUrl+'/recipes/find/'+id)
  }
  public  addRecipes(r:Recipe): Observable<Recipe>{
    return  this.httpClient.post<Recipe>(this.apiServerUrl+'/recipes/add/',r)
  }
  public  addRecipeImage(r:Recipe): Observable<Recipe>{
    return  this.httpClient.put<Recipe>(this.apiServerUrl+'/recipes/addImage/',r)
  }
  public  updateRecipe(r:Recipe): Observable<Recipe>{
    return  this.httpClient.put<Recipe>(this.apiServerUrl+'/recipes/update/',r)
  }
  public deleteRecipes(id:Number){
    const params = new HttpParams().set('id', id.toString());
     return this.httpClient.delete(this.apiServerUrl+'/recipes/delete/'+id);

  }
  public getRecipesFiltered(tags:String[]):Observable<Recipe[]> {
    const params = new HttpParams().set("tags",tags.toString());
    return this.httpClient.get<Recipe[]>(this.apiServerUrl+"/recipes/search/results/",{params});
  }
}
