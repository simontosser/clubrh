import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RecrutementProfil } from './recrutement-profil.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RecrutementProfil>;

@Injectable()
export class RecrutementProfilService {

    private resourceUrl =  SERVER_API_URL + 'api/recrutement-profils';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/recrutement-profils';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(recrutementProfil: RecrutementProfil): Observable<EntityResponseType> {
        const copy = this.convert(recrutementProfil);
        return this.http.post<RecrutementProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(recrutementProfil: RecrutementProfil): Observable<EntityResponseType> {
        const copy = this.convert(recrutementProfil);
        return this.http.put<RecrutementProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RecrutementProfil>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RecrutementProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<RecrutementProfil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RecrutementProfil[]>) => this.convertArrayResponse(res));
    }

    queryMe(req?: any): Observable<HttpResponse<RecrutementProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<RecrutementProfil[]>(this.resourceUrl + '/current', { params: options, observe: 'response' })
            .map((res: HttpResponse<RecrutementProfil[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<RecrutementProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<RecrutementProfil[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RecrutementProfil[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RecrutementProfil = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RecrutementProfil[]>): HttpResponse<RecrutementProfil[]> {
        const jsonResponse: RecrutementProfil[] = res.body;
        const body: RecrutementProfil[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RecrutementProfil.
     */
    private convertItemFromServer(recrutementProfil: RecrutementProfil): RecrutementProfil {
        const copy: RecrutementProfil = Object.assign({}, recrutementProfil);
        copy.deadline = this.dateUtils
            .convertLocalDateFromServer(recrutementProfil.deadline);
        return copy;
    }

    /**
     * Convert a RecrutementProfil to a JSON which can be sent to the server.
     */
    private convert(recrutementProfil: RecrutementProfil): RecrutementProfil {
        const copy: RecrutementProfil = Object.assign({}, recrutementProfil);
        copy.deadline = this.dateUtils
            .convertLocalDateToServer(recrutementProfil.deadline);
        return copy;
    }
}
