import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILocalisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from './localisation.service';
import { IEvenement } from 'app/shared/model/evenement.model';
import { EvenementService } from 'app/entities/evenement';

@Component({
    selector: 'jhi-localisation-update',
    templateUrl: './localisation-update.component.html'
})
export class LocalisationUpdateComponent implements OnInit {
    private _localisation: ILocalisation;
    isSaving: boolean;

    evenements: IEvenement[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private localisationService: LocalisationService,
        private evenementService: EvenementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ localisation }) => {
            this.localisation = localisation;
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
        if (this.localisation.id !== undefined) {
            this.subscribeToSaveResponse(this.localisationService.update(this.localisation));
        } else {
            this.subscribeToSaveResponse(this.localisationService.create(this.localisation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILocalisation>>) {
        result.subscribe((res: HttpResponse<ILocalisation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get localisation() {
        return this._localisation;
    }

    set localisation(localisation: ILocalisation) {
        this._localisation = localisation;
    }
}
