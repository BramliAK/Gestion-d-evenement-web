import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Dateevent } from 'app/shared/model/dateevent.model';
import { DateeventService } from './dateevent.service';
import { DateeventComponent } from './dateevent.component';
import { DateeventDetailComponent } from './dateevent-detail.component';
import { DateeventUpdateComponent } from './dateevent-update.component';
import { DateeventDeletePopupComponent } from './dateevent-delete-dialog.component';
import { IDateevent } from 'app/shared/model/dateevent.model';

@Injectable({ providedIn: 'root' })
export class DateeventResolve implements Resolve<IDateevent> {
    constructor(private service: DateeventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dateevent: HttpResponse<Dateevent>) => dateevent.body));
        }
        return of(new Dateevent());
    }
}

export const dateeventRoute: Routes = [
    {
        path: 'dateevent',
        component: DateeventComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'backendApp.dateevent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dateevent/:id/view',
        component: DateeventDetailComponent,
        resolve: {
            dateevent: DateeventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.dateevent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dateevent/new',
        component: DateeventUpdateComponent,
        resolve: {
            dateevent: DateeventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.dateevent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dateevent/:id/edit',
        component: DateeventUpdateComponent,
        resolve: {
            dateevent: DateeventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.dateevent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dateeventPopupRoute: Routes = [
    {
        path: 'dateevent/:id/delete',
        component: DateeventDeletePopupComponent,
        resolve: {
            dateevent: DateeventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'backendApp.dateevent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
