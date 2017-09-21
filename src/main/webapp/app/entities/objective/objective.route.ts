import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ObjectiveComponent } from './objective.component';
import { ObjectiveDetailComponent } from './objective-detail.component';
import { ObjectivePopupComponent } from './objective-dialog.component';
import { ObjectiveDeletePopupComponent } from './objective-delete-dialog.component';

@Injectable()
export class ObjectiveResolvePagingParams implements Resolve<any> {

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

export const objectiveRoute: Routes = [
    {
        path: 'objective',
        component: ObjectiveComponent,
        resolve: {
            'pagingParams': ObjectiveResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.objective.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'objective/:id',
        component: ObjectiveDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.objective.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const objectivePopupRoute: Routes = [
    {
        path: 'objective-new',
        component: ObjectivePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.objective.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'objective/:id/edit',
        component: ObjectivePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.objective.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'objective/:id/delete',
        component: ObjectiveDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.objective.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
