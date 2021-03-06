import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocalisation } from 'app/shared/model/localisation.model';

type EntityResponseType = HttpResponse<ILocalisation>;
type EntityArrayResponseType = HttpResponse<ILocalisation[]>;

@Injectable({ providedIn: 'root' })
export class LocalisationService {
    private resourceUrl = SERVER_API_URL + 'api/localisations';

    constructor(private http: HttpClient) {}

    create(localisation: ILocalisation): Observable<EntityResponseType> {
        return this.http.post<ILocalisation>(this.resourceUrl, localisation, { observe: 'response' });
    }

    update(localisation: ILocalisation): Observable<EntityResponseType> {
        return this.http.put<ILocalisation>(this.resourceUrl, localisation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILocalisation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILocalisation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
