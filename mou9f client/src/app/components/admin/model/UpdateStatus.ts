export class  UpdateStatus {
    status!:boolean;
    username!:string;

    constructor(username:string, status:boolean){
     this.username=username;
     this.status=status;
    }

}