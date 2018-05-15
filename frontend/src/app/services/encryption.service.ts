import { Injectable } from "@angular/core";

@Injectable()
export class EncrptionService{

    /*
    * encrypting the call using the 
    * Function to encrypt the idtoken before sending
    * Called inside the interceptor
    * param token:- is the id_token recieved from the Azure ad
    * param key:- is the url after the server address and the before params
    * param key:- example(http://localhost:4200/skill/getAllSkill) then 
    * param key:- getAllSkill is the key.
    */
    encryptToken(token:string, key:string):string {
        
        return null;
    }
}