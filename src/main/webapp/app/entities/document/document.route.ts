import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DocumentComponent } from './document.component';
import { DocumentDetailComponent } from './document-detail.component';
import { DocumentPopupComponent } from './document-dialog.component';
import { DocumentDeletePopupComponent } from './document-delete-dialog.component';

@Injectable()
export class DocumentResolvePagingParams implements Resolve<any> {

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

export const documentRoute: Routes = [
    {
        path: 'document',
        component: DocumentComponent,
        resolve: {
            'pagingParams': DocumentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.document.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'document/:id',
        component: DocumentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.document.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentPopupRoute: Routes = [
    {
        path: 'document-new',
        component: DocumentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.document.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document/:id/edit',
        component: DocumentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.document.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'document/:id/delete',
        component: DocumentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.document.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
