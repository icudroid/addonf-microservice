import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { JhiDateUtils } from 'ng-jhipster';

import { AdCampaing } from './ad-campaing.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AdCampaingService {

    private resourceUrl = '/ad/api/ad-campaings';
    private resourceSearchUrl = '/ad/api/_search/ad-campaings';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(adCampaing: AdCampaing): Observable<AdCampaing> {
        const copy = this.convert(adCampaing);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(adCampaing: AdCampaing): Observable<AdCampaing> {
        const copy = this.convert(adCampaing);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AdCampaing> {
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
     * Convert a returned JSON object to AdCampaing.
     */
    private convertItemFromServer(json: any): AdCampaing {
        const entity: AdCampaing = Object.assign(new AdCampaing(), json);
        entity.start = this.dateUtils
            .convertDateTimeFromServer(json.start);
        entity.end = this.dateUtils
            .convertDateTimeFromServer(json.end);
        return entity;
    }

    /**
     * Convert a AdCampaing to a JSON which can be sent to the server.
     */
    private convert(adCampaing: AdCampaing): AdCampaing {
        const copy: AdCampaing = Object.assign({}, adCampaing);

        copy.start = this.dateUtils.toDate(adCampaing.start);

        copy.end = this.dateUtils.toDate(adCampaing.end);
        return copy;
    }
}
