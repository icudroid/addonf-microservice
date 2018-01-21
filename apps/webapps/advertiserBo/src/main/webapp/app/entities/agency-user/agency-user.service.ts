import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AgencyUser } from './agency-user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AgencyUserService {

    private resourceUrl = '/ad/api/agency-users';
    private resourceSearchUrl = '/ad/api/_search/agency-users';

    constructor(private http: Http) { }

    create(agencyUser: AgencyUser): Observable<AgencyUser> {
        const copy = this.convert(agencyUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(agencyUser: AgencyUser): Observable<AgencyUser> {
        const copy = this.convert(agencyUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AgencyUser> {
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
     * Convert a returned JSON object to AgencyUser.
     */
    private convertItemFromServer(json: any): AgencyUser {
        const entity: AgencyUser = Object.assign(new AgencyUser(), json);
        return entity;
    }

    /**
     * Convert a AgencyUser to a JSON which can be sent to the server.
     */
    private convert(agencyUser: AgencyUser): AgencyUser {
        const copy: AgencyUser = Object.assign({}, agencyUser);
        return copy;
    }
}
