import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AdRule } from './ad-rule.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AdRuleService {

    private resourceUrl = '/ad/api/ad-rules';
    private resourceSearchUrl = '/ad/api/_search/ad-rules';

    constructor(private http: Http) { }

    create(adRule: AdRule): Observable<AdRule> {
        const copy = this.convert(adRule);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(adRule: AdRule): Observable<AdRule> {
        const copy = this.convert(adRule);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AdRule> {
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
     * Convert a returned JSON object to AdRule.
     */
    private convertItemFromServer(json: any): AdRule {
        const entity: AdRule = Object.assign(new AdRule(), json);
        return entity;
    }

    /**
     * Convert a AdRule to a JSON which can be sent to the server.
     */
    private convert(adRule: AdRule): AdRule {
        const copy: AdRule = Object.assign({}, adRule);
        return copy;
    }
}
