import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EmailTemplatesComponent } from './email-templates.component';
import { EmailTemplatesDetailComponent } from './email-templates-detail.component';
import { EmailTemplatesPopupComponent } from './email-templates-dialog.component';
import { EmailTemplatesDeletePopupComponent } from './email-templates-delete-dialog.component';

@Injectable()
export class EmailTemplatesResolvePagingParams implements Resolve<any> {

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

export const emailTemplatesRoute: Routes = [
    {
        path: 'email-templates',
        component: EmailTemplatesComponent,
        resolve: {
            'pagingParams': EmailTemplatesResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.emailTemplates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'email-templates/:id',
        component: EmailTemplatesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.emailTemplates.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailTemplatesPopupRoute: Routes = [
    {
        path: 'email-templates-new',
        component: EmailTemplatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.emailTemplates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-templates/:id/edit',
        component: EmailTemplatesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.emailTemplates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-templates/:id/delete',
        component: EmailTemplatesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.emailTemplates.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
