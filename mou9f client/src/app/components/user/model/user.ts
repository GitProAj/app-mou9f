export class user{
    username!:string;
    password!:string;
    roles:string[]=[];

    constructor(username:string, password:string, role:string[]){
     this.username=username,
     this.password=password,
     this.roles=role
    }

}