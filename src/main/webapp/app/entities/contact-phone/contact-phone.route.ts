import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ContactPhoneComponent } from './contact-phone.component';
import { ContactPhoneDetailComponent } from './contact-phone-detail.component';
import { ContactPhonePopupComponent } from './contact-phone-dialog.component';
import { ContactPhoneDeletePopupComponent } from './contact-phone-delete-dialog.component';

@Injectable()
export class ContactPhoneResolvePagingParams implements Resolve<any> {

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

export const contactPhoneRoute: Routes = [
    {
        path: 'contact-phone',
        component: ContactPhoneComponent,
        resolve: {
            'pagingParams': ContactPhoneResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactPhone.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'contact-phone/:id',
        component: ContactPhoneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactPhone.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const contactPhonePopupRoute: Routes = [
    {
        path: 'contact-phone-new',
        component: ContactPhonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactPhone.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-phone/:id/edit',
        component: ContactPhonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactPhone.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'contact-phone/:id/delete',
        component: ContactPhoneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.contactPhone.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
