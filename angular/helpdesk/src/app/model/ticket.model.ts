import { User } from "./user.model";

export class Ticket {
    constructor(
        public id: string | null,
        public number: number,
        public title: string,
        public status: string,
        public priority: string, 
        public image: string, 
        public user: User, 
        public assignedUser: User,
        public date: string,
        public changes: Array<string> 
    ){}
}