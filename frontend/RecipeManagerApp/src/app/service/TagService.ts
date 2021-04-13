import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Recipe} from "../entity/Recipe";
import {HttpClient} from "@angular/common/http";
import {Tag} from "../entity/Tag";

@Injectable({
  providedIn:'root'
})
export class TagService{
  private  apiServerUrl=environment.apiBaseUrl;

  constructor(private  httpClient: HttpClient) {

  }
  public  getTags(): Observable<Tag[]>{
    return  this.httpClient.get<Tag[]>(this.apiServerUrl+'/recipes/tags/all')
  }
}
