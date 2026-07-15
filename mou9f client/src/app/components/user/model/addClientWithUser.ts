import { client } from "./client";
import { user } from "./user";

export class addClientWithUser{
  
   client!: client;
   user !:user;
   constructor(clien:client, usr:user){
    this.client=clien;
    this.user=usr;
   }
}