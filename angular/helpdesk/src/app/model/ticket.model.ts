import { ChangeStatus } from "./changeStatus.model";
import { User } from "./user.model";

export class Ticket {
    constructor(
        public id: string | null,
        public number: number | null,
        public title: string,
        public status: string,
        public priority: string, 
        public image: string, 
        public user: User | null, 
        public assignedUser: User | null,
        public date: string,
        public changes: Array<ChangeStatus>,
        public description: string
    ){}
}