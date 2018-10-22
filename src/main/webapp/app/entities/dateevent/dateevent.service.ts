import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDateevent } from 'app/shared/model/dateevent.model';

type EntityResponseType = HttpResponse<IDateevent>;
type EntityArrayResponseType = HttpResponse<IDateevent[]>;

@Injectable({ providedIn: 'root' })
export class DateeventService {
    private resourceUrl = SERVER_API_URL + 'api/dateevents';

    constructor(private http: HttpClient) {}

    create(dateevent: IDateevent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dateevent);
        return this.http
            .post<IDateevent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dateevent: IDateevent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dateevent);
        return this.http
            .put<IDateevent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDateevent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDateevent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(dateevent: IDateevent): IDateevent {
        const copy: IDateevent = Object.assign({}, dateevent, {
            date: dateevent.date != null && dateevent.date.isValid() ? dateevent.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((dateevent: IDateevent) => {
            dateevent.date = dateevent.date != null ? moment(dateevent.date) : null;
        });
        return res;
    }
}
