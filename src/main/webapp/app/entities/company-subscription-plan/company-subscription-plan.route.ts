import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CompanySubscriptionPlanComponent } from './company-subscription-plan.component';
import { CompanySubscriptionPlanDetailComponent } from './company-subscription-plan-detail.component';
import { CompanySubscriptionPlanPopupComponent } from './company-subscription-plan-dialog.component';
import { CompanySubscriptionPlanDeletePopupComponent } from './company-subscription-plan-delete-dialog.component';

@Injectable()
export class CompanySubscriptionPlanResolvePagingParams implements Resolve<any> {

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

export const companySubscriptionPlanRoute: Routes = [
    {
        path: 'company-subscription-plan',
        component: CompanySubscriptionPlanComponent,
        resolve: {
            'pagingParams': CompanySubscriptionPlanResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.companySubscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'company-subscription-plan/:id',
        component: CompanySubscriptionPlanDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.companySubscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const companySubscriptionPlanPopupRoute: Routes = [
    {
        path: 'company-subscription-plan-new',
        component: CompanySubscriptionPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.companySubscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-subscription-plan/:id/edit',
        component: CompanySubscriptionPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.companySubscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'company-subscription-plan/:id/delete',
        component: CompanySubscriptionPlanDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.companySubscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
