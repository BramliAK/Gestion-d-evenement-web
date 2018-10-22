import { IUser } from 'app/core/user/user.model';
import { IEvenement } from 'app/shared/model//evenement.model';

export interface ICommentaire {
    id?: number;
    commentaire?: string;
    user?: IUser;
    evenement?: IEvenement;
}

export class Commentaire implements ICommentaire {
    constructor(public id?: number, public commentaire?: string, public user?: IUser, public evenement?: IEvenement) {}
}
