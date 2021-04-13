import {Component, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";
import {Recipe} from "../entity/Recipe";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn:'root'
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
    return  this.httpClient.post<Recipe>(this.apiServerUrl+'}/recipes/add',r)
  }
  public  updateRecipes(r:Recipe): Observable<Recipe>{
    return  this.httpClient.put<Recipe>(this.apiServerUrl+'recipes/update',r)
  }
  public  deleteRecipes(id:Number): Observable<void>{
    return  this.httpClient.delete<void>(this.apiServerUrl+'/recipes/delete/${id}',)
  }
}
