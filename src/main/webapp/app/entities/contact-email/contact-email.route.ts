import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContactEmailComponent } from './contact-email.component';
import { ContactEmailDetailComponent } from './contact-email-detail.component';
import { ContactEmailPopupComponent } from './contact-email-dialog.component';
import { ContactEmailDeletePopupComponent } from './contact-email-delete-dialog.component';

@Injectable()
export class ContactEmailResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const contactEmailRoute: Routes = [
    {
        path: 'contact-email',
        component: ContactEmailComponent,
        resolve: {
            'pagingParams': ContactEmailResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactEmail.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contact-email/:id',
        component: ContactEmailDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactEmail.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactEmailPopupRoute: Routes = [
    {
        path: 'contact-email-new',
        component: ContactEmailPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactEmail.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-email/:id/edit',
        component: ContactEmailPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactEmail.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-email/:id/delete',
        component: ContactEmailDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactEmail.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
