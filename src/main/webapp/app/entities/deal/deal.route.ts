import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DealComponent } from './deal.component';
import { DealDetailComponent } from './deal-detail.component';
import { DealPopupComponent } from './deal-dialog.component';
import { DealDeletePopupComponent } from './deal-delete-dialog.component';

@Injectable()
export class DealResolvePagingParams implements Resolve<any> {

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

export const dealRoute: Routes = [
    {
        path: 'deal',
        component: DealComponent,
        resolve: {
            'pagingParams': DealResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.deal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'deal/:id',
        component: DealDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.deal.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dealPopupRoute: Routes = [
    {
        path: 'deal-new',
        component: DealPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.deal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deal/:id/edit',
        component: DealPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.deal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deal/:id/delete',
        component: DealDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.deal.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
