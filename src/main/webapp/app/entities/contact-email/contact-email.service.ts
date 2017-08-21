import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ContactEmail } from './contact-email.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ContactEmailService {

    private resourceUrl = 'api/contact-emails';

    constructor(private http: Http) { }

    create(contactEmail: ContactEmail): Observable<ContactEmail> {
        const copy = this.convert(contactEmail);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(contactEmail: ContactEmail): Observable<ContactEmail> {
        const copy = this.convert(contactEmail);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ContactEmail> {
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

    private convert(contactEmail: ContactEmail): ContactEmail {
        const copy: ContactEmail = Object.assign({}, contactEmail);
        return copy;
    }
}
