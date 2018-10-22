import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEvenement } from 'app/shared/model/evenement.model';

@Component({
    selector: 'jhi-evenement-detail',
    templateUrl: './evenement-detail.component.html'
})
export class EvenementDetailComponent implements OnInit {
    evenement: IEvenement;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ evenement }) => {
            this.evenement = evenement;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
