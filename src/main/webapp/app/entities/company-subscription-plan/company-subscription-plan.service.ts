import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { CompanySubscriptionPlan } from './company-subscription-plan.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanySubscriptionPlanService {

    private resourceUrl = 'api/company-subscription-plans';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companySubscriptionPlan: CompanySubscriptionPlan): Observable<CompanySubscriptionPlan> {
        const copy = this.convert(companySubscriptionPlan);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(companySubscriptionPlan: CompanySubscriptionPlan): Observable<CompanySubscriptionPlan> {
        const copy = this.convert(companySubscriptionPlan);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CompanySubscriptionPlan> {
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
        entity.startDate = this.dateUtils
            .convertDateTimeFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertDateTimeFromServer(entity.endDate);
    }

    private convert(companySubscriptionPlan: CompanySubscriptionPlan): CompanySubscriptionPlan {
        const copy: CompanySubscriptionPlan = Object.assign({}, companySubscriptionPlan);

        copy.startDate = this.dateUtils.toDate(companySubscriptionPlan.startDate);

        copy.endDate = this.dateUtils.toDate(companySubscriptionPlan.endDate);
        return copy;
    }
}
