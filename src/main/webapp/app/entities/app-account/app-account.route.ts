import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AppAccountComponent } from './app-account.component';
import { AppAccountDetailComponent } from './app-account-detail.component';
import { AppAccountPopupComponent } from './app-account-dialog.component';
import { AppAccountDeletePopupComponent } from './app-account-delete-dialog.component';

@Injectable()
export class AppAccountResolvePagingParams implements Resolve<any> {

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

export const appAccountRoute: Routes = [
    {
        path: 'app-account',
        component: AppAccountComponent,
        resolve: {
            'pagingParams': AppAccountResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.appAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'app-account/:id',
        component: AppAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.appAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appAccountPopupRoute: Routes = [
    {
        path: 'app-account-new',
        component: AppAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.appAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-account/:id/edit',
        component: AppAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.appAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-account/:id/delete',
        component: AppAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.appAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
