import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Pipeline } from './pipeline.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PipelineService {

    private resourceUrl = 'api/pipelines';

    constructor(private http: Http) { }

    create(pipeline: Pipeline): Observable<Pipeline> {
        const copy = this.convert(pipeline);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(pipeline: Pipeline): Observable<Pipeline> {
        const copy = this.convert(pipeline);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Pipeline> {
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

    private convert(pipeline: Pipeline): Pipeline {
        const copy: Pipeline = Object.assign({}, pipeline);
        return copy;
    }
}
