import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Sector } from './sector.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SectorService {

    private resourceUrl = '/ad/api/sectors';
    private resourceSearchUrl = '/ad/api/_search/sectors';

    constructor(private http: Http) { }

    create(sector: Sector): Observable<Sector> {
        const copy = this.convert(sector);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(sector: Sector): Observable<Sector> {
        const copy = this.convert(sector);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Sector> {
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
     * Convert a returned JSON object to Sector.
     */
    private convertItemFromServer(json: any): Sector {
        const entity: Sector = Object.assign(new Sector(), json);
        return entity;
    }

    /**
     * Convert a Sector to a JSON which can be sent to the server.
     */
    private convert(sector: Sector): Sector {
        const copy: Sector = Object.assign({}, sector);
        return copy;
    }
}
