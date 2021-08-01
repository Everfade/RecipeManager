import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrls: ['./start.component.scss']
})
export class StartComponent implements OnInit {
  randomGreetingMorning:String[]=["Good Morning","It's a beautiful Morning","It's cooking time"]
  randomGreetingNoon:String[]=["Hope you're day is going great","It's cooking time","Nice to see you"]
  randomGreetingEvening:String[]=["Hope you had a nice day!","Good evening","Hows it going?"]
  selectedGreeting:String=""

  images = [1, 2, 3,4,5].map((n) => `../assets/palm${n}.png`);
  constructor() { }

  ngOnInit(): void {
    var index=Math.floor(Math.random() * (2 + 1));
    var d= new Date();
    var hours=d.getHours();

    if(hours> 17){
        this.selectedGreeting=this.randomGreetingEvening[index];
    }
    else if(hours> 12){
      this.selectedGreeting=this.randomGreetingNoon[index];
    }
    else {
      this.selectedGreeting=this.randomGreetingEvening[index];
    }

  }


}
