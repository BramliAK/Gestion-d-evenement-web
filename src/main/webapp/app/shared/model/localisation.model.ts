import { IEvenement } from 'app/shared/model//evenement.model';

export const enum Emplacement {
    Ariana = 'Ariana',
    Beja = 'Beja',
    Ben_Arous = 'Ben_Arous',
    Bizerte = 'Bizerte',
    Gabes = 'Gabes',
    Gafsa = 'Gafsa',
    Jendouba = 'Jendouba',
    Kairouan = 'Kairouan',
    Kasserine = 'Kasserine',
    Kebili = 'Kebili',
    Kef = 'Kef',
    Mahdia = 'Mahdia',
    Manouba = 'Manouba',
    Medenine = 'Medenine',
    Monastir = 'Monastir',
    Nabeul = 'Nabeul',
    Sfax = 'Sfax',
    Sidi_Bouzid = 'Sidi_Bouzid',
    Siliana = 'Siliana',
    Sousse = 'Sousse',
    Tataouine = 'Tataouine',
    Tozeur = 'Tozeur',
    Tunis = 'Tunis',
    Zaghouan = 'Zaghouan'
}

export interface ILocalisation {
    id?: number;
    nomemplacement?: string;
    emplacement?: Emplacement;
    longitude?: number;
    latitude?: number;
    evenements?: IEvenement[];
}

export class Localisation implements ILocalisation {
    constructor(
        public id?: number,
        public nomemplacement?: string,
        public emplacement?: Emplacement,
        public longitude?: number,
        public latitude?: number,
        public evenements?: IEvenement[]
    ) {}
}
