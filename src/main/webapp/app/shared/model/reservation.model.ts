import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IEvenement } from 'app/shared/model//evenement.model';

export interface IReservation {
    id?: number;
    date?: Moment;
    user?: IUser;
    evenement?: IEvenement;
}

export class Reservation implements IReservation {
    constructor(public id?: number, public date?: Moment, public user?: IUser, public evenement?: IEvenement) {}
}
