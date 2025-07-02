import { Ticket } from './ticket.model';
import { User } from './user.model';
export class ChangeStatus {
    constructor(
        public id: string,
        public ticket: Ticket,
        public userChange: User,
        public dateChangeStatus: string,
        public status: string
    ){}
}