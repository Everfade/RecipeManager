import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Recipe} from "../entity/Recipe";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Tag} from "../entity/Tag";

@Injectable({
  providedIn:'root'
})
export class TagService{
  private  apiServerUrl=environment.apiBaseUrl;

  constructor(private  httpClient: HttpClient) {

  }
  public  getTags(): Observable<Tag[]>{
    return  this.httpClient.get<Tag[]>(this.apiServerUrl+'/tags/all')
  }
  public  postTag(t:Tag): Observable<Tag>{
    return  this.httpClient.post<Tag>(this.apiServerUrl+'/tags/add/',t);
  }
  public deleteTag(id:Number){
    const params = new HttpParams().set('id', id.toString());
    return this.httpClient.delete(this.apiServerUrl+'/tags/delete/'+id);


  }
}
