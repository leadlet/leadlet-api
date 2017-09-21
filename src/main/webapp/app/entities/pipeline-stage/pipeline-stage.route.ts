import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PipelineStageComponent } from './pipeline-stage.component';
import { PipelineStageDetailComponent } from './pipeline-stage-detail.component';
import { PipelineStagePopupComponent } from './pipeline-stage-dialog.component';
import { PipelineStageDeletePopupComponent } from './pipeline-stage-delete-dialog.component';

@Injectable()
export class PipelineStageResolvePagingParams implements Resolve<any> {

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

export const pipelineStageRoute: Routes = [
    {
        path: 'pipeline-stage',
        component: PipelineStageComponent,
        resolve: {
            'pagingParams': PipelineStageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.pipelineStage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pipeline-stage/:id',
        component: PipelineStageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.pipelineStage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pipelineStagePopupRoute: Routes = [
    {
        path: 'pipeline-stage-new',
        component: PipelineStagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.pipelineStage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pipeline-stage/:id/edit',
        component: PipelineStagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.pipelineStage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pipeline-stage/:id/delete',
        component: PipelineStageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'leadletApiApp.pipelineStage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
