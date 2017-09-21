import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { StageComponent } from './stage.component';
import { StageDetailComponent } from './stage-detail.component';
import { StagePopupComponent } from './stage-dialog.component';
import { StageDeletePopupComponent } from './stage-delete-dialog.component';

@Injectable()
export class StageResolvePagingParams implements Resolve<any> {

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

export const stageRoute: Routes = [
    {
        path: 'stage',
        component: StageComponent,
        resolve: {
            'pagingParams': StageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.stage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stage/:id',
        component: StageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.stage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stagePopupRoute: Routes = [
    {
        path: 'stage-new',
        component: StagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.stage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stage/:id/edit',
        component: StagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.stage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stage/:id/delete',
        component: StageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.stage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
