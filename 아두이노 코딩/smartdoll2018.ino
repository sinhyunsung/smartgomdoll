
#include <Servo.h>
String operStart="";
String oper="";
String blt="";
String angleString="";

int comma,next,angle;
int moternum=1;

Servo moter1;
Servo moter2;
Servo moter3;



void setup() {
   Serial.begin(9600);
  Serial1.begin(9600);

  
}


void play(){
Serial.println("시작완료");
operStart=oper;

while(operStart.indexOf('>')!=-1){

next=operStart.indexOf('>');
comma=operStart.lastIndexOf(',',next);

Serial.println(moternum);









switch(moternum){
  case 1:{
   
if(comma==-1){
  switch(operStart.charAt(0)){
    case 'f':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(130);
      moter2.write(60);
      break;
    }
    case 'b':{
      moter1.attach(9);
      moter2.attach(10);
       moter1.write(60);
      moter2.write(130);
      break;
    }
    case 'r':{
      moter1.attach(9);
      moter2.attach(10);
       moter1.write(130);
      moter2.write(130);
      break;
    }
    case 'l':{
      moter1.attach(9);
      moter2.attach(10);
       moter1.write(60);
      moter2.write(60);
      break;
    }
    case 'p':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(96);
      moter2.write(96);
      break;
    }
  }
  
}else{
  for(int i=2; i<=next-1; i++){
  angleString+=operStart.charAt(i);
  }
  
  
  angle=angleString.toInt();
  Serial.println(angle);
  angleString="";
  
  switch(operStart.charAt(0)){
    case '1':{
        moter1.attach(36);
        moter1.write(angle);
        break;
    }
    case '2':{
        moter1.attach(37);
        moter1.write(angle);
        break;
    }
    case '3':{
        moter1.attach(38);  
        moter1.write(angle);
        break;
    }
    case '4':{
      moter1.attach(39);
      moter1.write(angle);
      break;
    }
    case '5':{
      moter1.attach(40);
      moter1.write(angle);
      break;
    }
    case '6':{
      moter1.attach(41);
      moter1.write(angle);
      break;
    }
     case 'd':{
        delay(angle);
        break;
    }
  }
}
break;
  }
  case 2:{
    
if(comma==-1){
  switch(operStart.charAt(0)){
    case 'f':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(130);
      moter2.write(60);
      break;
    }
    case 'b':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(60);
      moter2.write(130);
      break;
    }
    case 'r':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(130);
      moter2.write(130);
      break;
    }
    case 'l':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(60);
      moter2.write(60);
      break;
    case 'p':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(96);
      moter2.write(96);
      break;
    }
    }
  }
  
}else{
  for(int i=2; i<=next-1; i++){
  angleString+=operStart.charAt(i);
  }
  
  
  angle=angleString.toInt();
  Serial.println(angle);
  angleString="";
  
  switch(operStart.charAt(0)){
    case '1':{
        moter2.attach(36);
        moter2.write(angle);
        break;
    }
    case '2':{
        moter2.attach(37);
        moter2.write(angle);
        break;
    }
    case '3':{
        moter2.attach(38);  
        moter2.write(angle);
        break;
    }
    case '4':{
      moter2.attach(39);
      moter2.write(angle);
      break;
    }
    case '5':{
      moter2.attach(40);
      moter2.write(angle);
      break;
    }
    case '6':{
      moter2.attach(41);
      moter2.write(angle);
      break;
    }
     case 'd':{
        delay(angle);
        break;
    }
  }
}
break;
  }
  case 3:{
    
if(comma==-1){
  switch(operStart.charAt(0)){
    case 'f':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(130);
      moter2.write(60);
      break;
    }
    case 'b':{
      moter1.attach(9);
      moter2.attach(10);
       moter1.write(60);
      moter2.write(130);
      break;
    }
    case 'r':{
      moter1.attach(9);
      moter2.attach(10);
       moter1.write(130);
      moter2.write(130);
      break;
    }
    case 'l':{
      moter1.attach(9);
      moter2.attach(10);
       moter1.write(60);
      moter2.write(60);
      break;
    }
    case 'p':{
      moter1.attach(9);
      moter2.attach(10);
      moter1.write(96);
      moter2.write(96);
      break;
    }
  }
  
}else{
  for(int i=2; i<=next-1; i++){
  angleString+=operStart.charAt(i);
  }
  
  
  angle=angleString.toInt();
  Serial.println(angle);
  angleString="";
  
  switch(operStart.charAt(0)){
    case '1':{
        moter3.attach(36);
        moter3.write(angle);
        break;
    }
    case '2':{
        moter3.attach(37);
        moter3.write(angle);
        break;
    }
    case '3':{
        moter3.attach(38);  
        moter3.write(angle);
        break;
    }
    case '4':{
      moter3.attach(39);
      moter3.write(angle);
      break;
    }
    case '5':{
      moter3.attach(40);
      moter3.write(angle);
      break;
    }
    case '6':{
      moter3.attach(41);
      moter3.write(angle);
      break;
    }
     case 'd':{
        delay(angle);
        break;
    }
  }
}
break;
  }
 












}

if(comma==-1){
   moternum+=1;
if(moternum==4){
  moternum=1;
}
 moternum+=1;
if(moternum==4){
  moternum=1;
}

}else{
if(operStart.charAt(0)!='d'&&operStart.charAt(0)!='p'){
 moternum+=1;
if(moternum==4){
  moternum=1;
}
}
}

operStart.remove(0,next+1);
}

  operStart="";
}









void loop() { 
 
 if (Serial1.available()) {
    blt="";
    
    while(Serial1.available()){
      delay(10);
    blt+=Serial1.readString();
    }
      
    blt+='\0';
   Serial.println(blt);

    if(blt.equals("go")){
      play();
    }else{
      oper=blt;
    }
   
  }
  

}
