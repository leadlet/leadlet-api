import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Objective } from './objective.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ObjectiveService {

    private resourceUrl = 'api/objectives';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(objective: Objective): Observable<Objective> {
        const copy = this.convert(objective);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(objective: Objective): Observable<Objective> {
        const copy = this.convert(objective);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Objective> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dueDate = this.dateUtils
            .convertDateTimeFromServer(entity.dueDate);
    }

    private convert(objective: Objective): Objective {
        const copy: Objective = Object.assign({}, objective);

        copy.dueDate = this.dateUtils.toDate(objective.dueDate);
        return copy;
    }
}
