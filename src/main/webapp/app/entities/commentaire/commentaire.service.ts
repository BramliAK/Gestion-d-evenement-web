import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommentaire } from 'app/shared/model/commentaire.model';

type EntityResponseType = HttpResponse<ICommentaire>;
type EntityArrayResponseType = HttpResponse<ICommentaire[]>;

@Injectable({ providedIn: 'root' })
export class CommentaireService {
    private resourceUrl = SERVER_API_URL + 'api/commentaires';

    constructor(private http: HttpClient) {}

    create(commentaire: ICommentaire): Observable<EntityResponseType> {
        return this.http.post<ICommentaire>(this.resourceUrl, commentaire, { observe: 'response' });
    }

    update(commentaire: ICommentaire): Observable<EntityResponseType> {
        return this.http.put<ICommentaire>(this.resourceUrl, commentaire, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICommentaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICommentaire[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
