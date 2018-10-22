import { Moment } from 'moment';
import { IEvenement } from 'app/shared/model//evenement.model';

export interface IDateevent {
    id?: number;
    date?: Moment;
    evenements?: IEvenement[];
}

export class Dateevent implements IDateevent {
    constructor(public id?: number, public date?: Moment, public evenements?: IEvenement[]) {}
}
