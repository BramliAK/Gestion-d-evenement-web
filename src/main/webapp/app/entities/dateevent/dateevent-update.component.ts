import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IDateevent } from 'app/shared/model/dateevent.model';
import { DateeventService } from './dateevent.service';
import { IEvenement } from 'app/shared/model/evenement.model';
import { EvenementService } from 'app/entities/evenement';

@Component({
    selector: 'jhi-dateevent-update',
    templateUrl: './dateevent-update.component.html'
})
export class DateeventUpdateComponent implements OnInit {
    private _dateevent: IDateevent;
    isSaving: boolean;

    evenements: IEvenement[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private dateeventService: DateeventService,
        private evenementService: EvenementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dateevent }) => {
            this.dateevent = dateevent;
        });
        this.evenementService.query().subscribe(
            (res: HttpResponse<IEvenement[]>) => {
                this.evenements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.dateevent.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.dateevent.id !== undefined) {
            this.subscribeToSaveResponse(this.dateeventService.update(this.dateevent));
        } else {
            this.subscribeToSaveResponse(this.dateeventService.create(this.dateevent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDateevent>>) {
        result.subscribe((res: HttpResponse<IDateevent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEvenementById(index: number, item: IEvenement) {
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
    get dateevent() {
        return this._dateevent;
    }

    set dateevent(dateevent: IDateevent) {
        this._dateevent = dateevent;
        this.date = moment(dateevent.date).format(DATE_TIME_FORMAT);
    }
}
