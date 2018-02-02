import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DocumentProfil } from './document-profil.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DocumentProfil>;

@Injectable()
export class DocumentProfilService {

    private resourceUrl =  SERVER_API_URL + 'api/document-profils';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/document-profils';

    constructor(private http: HttpClient) { }

    create(documentProfil: DocumentProfil): Observable<EntityResponseType> {
        const copy = this.convert(documentProfil);
        return this.http.post<DocumentProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(documentProfil: DocumentProfil): Observable<EntityResponseType> {
        const copy = this.convert(documentProfil);
        return this.http.put<DocumentProfil>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DocumentProfil>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DocumentProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<DocumentProfil[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DocumentProfil[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DocumentProfil[]>> {
        const options = createRequestOption(req);
        return this.http.get<DocumentProfil[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DocumentProfil[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DocumentProfil = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DocumentProfil[]>): HttpResponse<DocumentProfil[]> {
        const jsonResponse: DocumentProfil[] = res.body;
        const body: DocumentProfil[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DocumentProfil.
     */
    private convertItemFromServer(documentProfil: DocumentProfil): DocumentProfil {
        const copy: DocumentProfil = Object.assign({}, documentProfil);
        return copy;
    }

    /**
     * Convert a DocumentProfil to a JSON which can be sent to the server.
     */
    private convert(documentProfil: DocumentProfil): DocumentProfil {
        const copy: DocumentProfil = Object.assign({}, documentProfil);
        return copy;
    }
}
