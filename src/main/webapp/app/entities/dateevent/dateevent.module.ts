import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BackendSharedModule } from 'app/shared';
import {
    DateeventComponent,
    DateeventDetailComponent,
    DateeventUpdateComponent,
    DateeventDeletePopupComponent,
    DateeventDeleteDialogComponent,
    dateeventRoute,
    dateeventPopupRoute
} from './';

const ENTITY_STATES = [...dateeventRoute, ...dateeventPopupRoute];

@NgModule({
    imports: [BackendSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DateeventComponent,
        DateeventDetailComponent,
        DateeventUpdateComponent,
        DateeventDeleteDialogComponent,
        DateeventDeletePopupComponent
    ],
    entryComponents: [DateeventComponent, DateeventUpdateComponent, DateeventDeleteDialogComponent, DateeventDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BackendDateeventModule {}
