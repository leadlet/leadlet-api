import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SubscriptionPlanComponent } from './subscription-plan.component';
import { SubscriptionPlanDetailComponent } from './subscription-plan-detail.component';
import { SubscriptionPlanPopupComponent } from './subscription-plan-dialog.component';
import { SubscriptionPlanDeletePopupComponent } from './subscription-plan-delete-dialog.component';

@Injectable()
export class SubscriptionPlanResolvePagingParams implements Resolve<any> {

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

export const subscriptionPlanRoute: Routes = [
    {
        path: 'subscription-plan',
        component: SubscriptionPlanComponent,
        resolve: {
            'pagingParams': SubscriptionPlanResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.subscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'subscription-plan/:id',
        component: SubscriptionPlanDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.subscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subscriptionPlanPopupRoute: Routes = [
    {
        path: 'subscription-plan-new',
        component: SubscriptionPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.subscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subscription-plan/:id/edit',
        component: SubscriptionPlanPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.subscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subscription-plan/:id/delete',
        component: SubscriptionPlanDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.subscriptionPlan.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
