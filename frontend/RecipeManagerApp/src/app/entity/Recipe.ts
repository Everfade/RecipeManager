import {Tag} from "./Tag";

export interface  Recipe{
  id:number;
  name:string;
  description:string;
  ingredients:string;
  instructions:[];
  tags:number[];
}
