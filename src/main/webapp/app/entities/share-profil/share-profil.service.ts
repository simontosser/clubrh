import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ShareProfil } from './share-profil.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ShareProfil>;

@Injectable()
export class ShareProfilService {

    private resourceUrl =  SERVER_API_URL + 'api/share-profils';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/share-profils';

    constructor(private http: HttpClient) { }

    create(shareProfil: ShareProfil): Observable<EntityResponseType> {
        const copy = this.convert(shareProfil);
        return this.http.post<ShareProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(shareProfil: ShareProfil): Observable<EntityResponseType> {
        const copy = this.convert(shareProfil);
        return this.http.put<ShareProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ShareProfil>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ShareProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<ShareProfil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ShareProfil[]>) => this.convertArrayResponse(res));
    }

    queryMe(req?: any): Observable<HttpResponse<ShareProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<ShareProfil[]>(this.resourceUrl + '/current', { params: options, observe: 'response' })
            .map((res: HttpResponse<ShareProfil[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ShareProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<ShareProfil[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ShareProfil[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ShareProfil = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ShareProfil[]>): HttpResponse<ShareProfil[]> {
        const jsonResponse: ShareProfil[] = res.body;
        const body: ShareProfil[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ShareProfil.
     */
    private convertItemFromServer(shareProfil: ShareProfil): ShareProfil {
        const copy: ShareProfil = Object.assign({}, shareProfil);
        return copy;
    }

    /**
     * Convert a ShareProfil to a JSON which can be sent to the server.
     */
    private convert(shareProfil: ShareProfil): ShareProfil {
        const copy: ShareProfil = Object.assign({}, shareProfil);
        return copy;
    }
}
