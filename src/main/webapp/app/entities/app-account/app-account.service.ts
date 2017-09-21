import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { AppAccount } from './app-account.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AppAccountService {

    private resourceUrl = 'api/app-accounts';

    constructor(private http: Http) { }

    create(appAccount: AppAccount): Observable<AppAccount> {
        const copy = this.convert(appAccount);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(appAccount: AppAccount): Observable<AppAccount> {
        const copy = this.convert(appAccount);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<AppAccount> {
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

    private convert(appAccount: AppAccount): AppAccount {
        const copy: AppAccount = Object.assign({}, appAccount);
        return copy;
    }
}
