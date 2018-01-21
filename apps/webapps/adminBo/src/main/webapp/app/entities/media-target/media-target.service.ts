import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { MediaTarget } from './media-target.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MediaTargetService {

    private resourceUrl = '/ad/api/media-targets';
    private resourceSearchUrl = '/ad/api/_search/media-targets';

    constructor(private http: Http) { }

    create(mediaTarget: MediaTarget): Observable<MediaTarget> {
        const copy = this.convert(mediaTarget);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(mediaTarget: MediaTarget): Observable<MediaTarget> {
        const copy = this.convert(mediaTarget);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MediaTarget> {
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
     * Convert a returned JSON object to MediaTarget.
     */
    private convertItemFromServer(json: any): MediaTarget {
        const entity: MediaTarget = Object.assign(new MediaTarget(), json);
        return entity;
    }

    /**
     * Convert a MediaTarget to a JSON which can be sent to the server.
     */
    private convert(mediaTarget: MediaTarget): MediaTarget {
        const copy: MediaTarget = Object.assign({}, mediaTarget);
        return copy;
    }
}
