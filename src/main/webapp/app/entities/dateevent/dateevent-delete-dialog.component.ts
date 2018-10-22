import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDateevent } from 'app/shared/model/dateevent.model';
import { DateeventService } from './dateevent.service';

@Component({
    selector: 'jhi-dateevent-delete-dialog',
    templateUrl: './dateevent-delete-dialog.component.html'
})
export class DateeventDeleteDialogComponent {
    dateevent: IDateevent;

    constructor(private dateeventService: DateeventService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dateeventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'dateeventListModification',
                content: 'Deleted an dateevent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dateevent-delete-popup',
    template: ''
})
export class DateeventDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dateevent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DateeventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.dateevent = dateevent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
