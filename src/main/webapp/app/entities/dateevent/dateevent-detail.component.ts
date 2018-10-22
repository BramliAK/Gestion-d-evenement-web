import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDateevent } from 'app/shared/model/dateevent.model';

@Component({
    selector: 'jhi-dateevent-detail',
    templateUrl: './dateevent-detail.component.html'
})
export class DateeventDetailComponent implements OnInit {
    dateevent: IDateevent;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dateevent }) => {
            this.dateevent = dateevent;
        });
    }

    previousState() {
        window.history.back();
    }
}
