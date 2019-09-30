import { User } from '../user';

export class Message {
    id: number;
    fromUser: User;
    toUser: User;
    dateTime: Date;
    message: String;
    seen: boolean;
    fromRole: string; 
    deletedFrom: boolean;
    deletedTo: boolean;
}
