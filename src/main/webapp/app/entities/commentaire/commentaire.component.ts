import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICommentaire } from 'app/shared/model/commentaire.model';
import { Principal } from 'app/core';
import { CommentaireService } from './commentaire.service';

@Component({
    selector: 'jhi-commentaire',
    templateUrl: './commentaire.component.html'
})
export class CommentaireComponent implements OnInit, OnDestroy {
    commentaires: ICommentaire[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private commentaireService: CommentaireService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.commentaireService.query().subscribe(
            (res: HttpResponse<ICommentaire[]>) => {
                this.commentaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCommentaires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommentaire) {
        return item.id;
    }

    registerChangeInCommentaires() {
        this.eventSubscriber = this.eventManager.subscribe('commentaireListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
