export class client{
    
      firstname!:string;
      lastname!:string;
      username!:string;
      password!:string;
      ville_activite!:string;
      lieut_activite!:string;
      phone!:string;
      activite!:string;
      roles:string[]=[];

    constructor(    firstname:string,
                    lastname:string,
                    username:string,
                    password:string,
                    ville_activite:string,
                    lieut_activite:string,
                    phone:string,
                    activite:string,
                    role:string[]  
                  ){
      this.firstname=firstname;
      this.lastname=lastname;
      this.username=username;
      this.password=password;
      this.ville_activite=ville_activite;
      this.lieut_activite=lieut_activite;
      this.phone=phone;
      this.activite=activite;
      this.roles=role;
    }
}