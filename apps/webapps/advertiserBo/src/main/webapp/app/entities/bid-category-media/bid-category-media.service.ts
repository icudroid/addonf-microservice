import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { BidCategoryMedia } from './bid-category-media.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BidCategoryMediaService {

    private resourceUrl = '/ad/api/bid-category-medias';
    private resourceSearchUrl = '/ad/api/_search/bid-category-medias';

    constructor(private http: Http) { }

    create(bidCategoryMedia: BidCategoryMedia): Observable<BidCategoryMedia> {
        const copy = this.convert(bidCategoryMedia);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(bidCategoryMedia: BidCategoryMedia): Observable<BidCategoryMedia> {
        const copy = this.convert(bidCategoryMedia);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<BidCategoryMedia> {
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
     * Convert a returned JSON object to BidCategoryMedia.
     */
    private convertItemFromServer(json: any): BidCategoryMedia {
        const entity: BidCategoryMedia = Object.assign(new BidCategoryMedia(), json);
        return entity;
    }

    /**
     * Convert a BidCategoryMedia to a JSON which can be sent to the server.
     */
    private convert(bidCategoryMedia: BidCategoryMedia): BidCategoryMedia {
        const copy: BidCategoryMedia = Object.assign({}, bidCategoryMedia);
        return copy;
    }
}
