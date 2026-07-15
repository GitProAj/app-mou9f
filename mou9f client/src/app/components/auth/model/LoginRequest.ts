export class LoginRequest {
      username!:string;
      password!:string;
      constructor( usernam : string,  passwor:string){
        this.username=usernam;
        this.password =passwor;
      }
}