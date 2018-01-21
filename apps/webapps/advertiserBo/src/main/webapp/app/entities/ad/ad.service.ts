import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Ad } from './ad.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AdService {

    private resourceUrl = '/ad/api/ads';
    private resourceSearchUrl = '/ad/api/_search/ads';

    constructor(private http: Http) { }

    create(ad: Ad): Observable<Ad> {
        const copy = this.convert(ad);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(ad: Ad): Observable<Ad> {
        const copy = this.convert(ad);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Ad> {
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
     * Convert a returned JSON object to Ad.
     */
    private convertItemFromServer(json: any): Ad {
        const entity: Ad = Object.assign(new Ad(), json);
        return entity;
    }

    /**
     * Convert a Ad to a JSON which can be sent to the server.
     */
    private convert(ad: Ad): Ad {
        const copy: Ad = Object.assign({}, ad);
        return copy;
    }
}
