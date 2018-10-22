import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Localisation } from 'app/shared/model/localisation.model';
import { LocalisationService } from './localisation.service';
import { LocalisationComponent } from './localisation.component';
import { LocalisationDetailComponent } from './localisation-detail.component';
import { LocalisationUpdateComponent } from './localisation-update.component';
import { LocalisationDeletePopupComponent } from './localisation-delete-dialog.component';
import { ILocalisation } from 'app/shared/model/localisation.model';

@Injectable({ providedIn: 'root' })
export class LocalisationResolve implements Resolve<ILocalisation> {
    constructor(private service: LocalisationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((localisation: HttpResponse<Localisation>) => localisation.body));
        }
        return of(new Localisation());
    }
}

export const localisationRoute: Routes = [
    {
        path: 'localisation',
        component: LocalisationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'backendApp.localisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'localisation/:id/view',
        component: LocalisationDetailComponent,
        resolve: {
            localisation: LocalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.localisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'localisation/new',
        component: LocalisationUpdateComponent,
        resolve: {
            localisation: LocalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.localisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'localisation/:id/edit',
        component: LocalisationUpdateComponent,
        resolve: {
            localisation: LocalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.localisation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const localisationPopupRoute: Routes = [
    {
        path: 'localisation/:id/delete',
        component: LocalisationDeletePopupComponent,
        resolve: {
            localisation: LocalisationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.localisation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
