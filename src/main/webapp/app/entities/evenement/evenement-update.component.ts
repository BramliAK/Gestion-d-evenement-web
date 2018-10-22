import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IEvenement } from 'app/shared/model/evenement.model';
import { EvenementService } from './evenement.service';
import { IDateevent } from 'app/shared/model/dateevent.model';
import { DateeventService } from 'app/entities/dateevent';
import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from 'app/entities/localisation';

@Component({
    selector: 'jhi-evenement-update',
    templateUrl: './evenement-update.component.html'
})
export class EvenementUpdateComponent implements OnInit {
    private _evenement: IEvenement;
    isSaving: boolean;

    dateevents: IDateevent[];

    localisations: ILocalisation[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private evenementService: EvenementService,
        private dateeventService: DateeventService,
        private localisationService: LocalisationService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ evenement }) => {
            this.evenement = evenement;
        });
        this.dateeventService.query().subscribe(
            (res: HttpResponse<IDateevent[]>) => {
                this.dateevents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.localisationService.query().subscribe(
            (res: HttpResponse<ILocalisation[]>) => {
                this.localisations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.evenement, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.evenement.id !== undefined) {
            this.subscribeToSaveResponse(this.evenementService.update(this.evenement));
        } else {
            this.subscribeToSaveResponse(this.evenementService.create(this.evenement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEvenement>>) {
        result.subscribe((res: HttpResponse<IEvenement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDateeventById(index: number, item: IDateevent) {
        return item.id;
    }

    trackLocalisationById(index: number, item: ILocalisation) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get evenement() {
        return this._evenement;
    }

    set evenement(evenement: IEvenement) {
        this._evenement = evenement;
    }
}
