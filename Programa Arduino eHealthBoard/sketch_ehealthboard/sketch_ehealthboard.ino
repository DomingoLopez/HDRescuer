#include <PinChangeInt.h>
#include <eHealth.h>

//Posibles estados por los que vamos a pasar 
//WAITING_INSTRUCTIONS, SENDING_DATA
char action_received = ' ';
String status = "WAITING_INSTRUCTIONS";

//Variables del pulsioximetro
int cont = 0;


void setup() {
  
  Serial.begin(9600);  
  eHealth.initPulsioximeter();
  
  PCintPort::attachInterrupt(6, readPulsioximeter, RISING);

 
}

void loop() {
  
  if(status == "WAITING_INSTRUCTIONS")
    receiveInstructions();

  if(status == "SENDING_DATA"){
    sendData();
    //Por si hay algo en el buffer de lectura que sea 'S' o 'N'
    receiveInstructions(); 
    delay(200);
  }

  
}

void receiveInstructions(){
  
   if(Serial.available()){
     action_received = Serial.read();
  }  
  
  if(action_received != ' '){
    
    switch(action_received){
      
      case 'S' : 
          
            status = "SENDING_DATA"; 
            //Serial.println("Recibida instruccion de recoleccion");
          
        break;
 
      case 'N' : 
          
            status = "WAITING_INSTRUCTIONS"; 
            //Serial.println("Recibida parada. Esperando ordenes");
          
        break;  
    }     
  }
  
}


void sendData(){
  sendPulseData();
  sendOximeterData();
  sendAirFlowData();

  
}

void sendPulseData(){
  Serial.print("###pulse:");
  Serial.print(eHealth.getBPM());
  Serial.print('+'); 
}

void sendOximeterData(){
  Serial.print("###oxigensaturation:");
  Serial.print(eHealth.getOxygenSaturation());
  Serial.print('+'); 
}

void sendAirFlowData(){
  
  Serial.print("###airflow:");
  Serial.print(eHealth.getAirFlow());
  Serial.print('+');
}

void readPulsioximeter(){  

  cont ++;

  if (cont == 50) { 
    eHealth.readPulsioximeter();  
    cont = 0;
  }
}





