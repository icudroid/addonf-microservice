import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Agency } from './agency.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AgencyService {

    private resourceUrl = '/ad/api/agencies';
    private resourceSearchUrl = '/ad/api/_search/agencies';

    constructor(private http: Http) { }

    create(agency: Agency): Observable<Agency> {
        const copy = this.convert(agency);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(agency: Agency): Observable<Agency> {
        const copy = this.convert(agency);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Agency> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Agency.
     */
    private convertItemFromServer(json: any): Agency {
        const entity: Agency = Object.assign(new Agency(), json);
        return entity;
    }

    /**
     * Convert a Agency to a JSON which can be sent to the server.
     */
    private convert(agency: Agency): Agency {
        const copy: Agency = Object.assign({}, agency);
        return copy;
    }
}
