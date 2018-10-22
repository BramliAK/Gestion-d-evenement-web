import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BackendEvenementModule } from './evenement/evenement.module';
import { BackendDateeventModule } from './dateevent/dateevent.module';
import { BackendLocalisationModule } from './localisation/localisation.module';
import { BackendReservationModule } from './reservation/reservation.module';
import { BackendCommentaireModule } from './commentaire/commentaire.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BackendEvenementModule,
        BackendDateeventModule,
        BackendLocalisationModule,
        BackendReservationModule,
        BackendCommentaireModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BackendEntityModule {}
