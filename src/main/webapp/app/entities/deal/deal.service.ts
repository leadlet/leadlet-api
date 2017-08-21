import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Deal } from './deal.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DealService {

    private resourceUrl = 'api/deals';

    constructor(private http: Http) { }

    create(deal: Deal): Observable<Deal> {
        const copy = this.convert(deal);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(deal: Deal): Observable<Deal> {
        const copy = this.convert(deal);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Deal> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(deal: Deal): Deal {
        const copy: Deal = Object.assign({}, deal);
        return copy;
    }
}
