import { IDateevent } from 'app/shared/model//dateevent.model';
import { ILocalisation } from 'app/shared/model//localisation.model';

export const enum Typeevent {
    Film = 'Film',
    Theatre = 'Theatre',
    Club = 'Club',
    Games = 'Games',
    Livres = 'Livres',
    Autres = 'Autres'
}

export interface IEvenement {
    id?: number;
    nom?: string;
    description?: any;
    typeevnet?: Typeevent;
    imageContentType?: string;
    image?: any;
    prix?: number;
    dateevents?: IDateevent[];
    localisations?: ILocalisation[];
}

export class Evenement implements IEvenement {
    constructor(
        public id?: number,
        public nom?: string,
        public description?: any,
        public typeevnet?: Typeevent,
        public imageContentType?: string,
        public image?: any,
        public prix?: number,
        public dateevents?: IDateevent[],
        public localisations?: ILocalisation[]
    ) {}
}
