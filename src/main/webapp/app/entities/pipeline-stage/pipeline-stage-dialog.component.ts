import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PipelineStage } from './pipeline-stage.model';
import { PipelineStagePopupService } from './pipeline-stage-popup.service';
import { PipelineStageService } from './pipeline-stage.service';
import { Pipeline, PipelineService } from '../pipeline';
import { Stage, StageService } from '../stage';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pipeline-stage-dialog',
    templateUrl: './pipeline-stage-dialog.component.html'
})
export class PipelineStageDialogComponent implements OnInit {

    pipelineStage: PipelineStage;
    isSaving: boolean;

    pipelines: Pipeline[];

    stages: Stage[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pipelineStageService: PipelineStageService,
        private pipelineService: PipelineService,
        private stageService: StageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pipelineService.query()
            .subscribe((res: ResponseWrapper) => { this.pipelines = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.stageService.query()
            .subscribe((res: ResponseWrapper) => { this.stages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pipelineStage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pipelineStageService.update(this.pipelineStage));
        } else {
            this.subscribeToSaveResponse(
                this.pipelineStageService.create(this.pipelineStage));
        }
    }

    private subscribeToSaveResponse(result: Observable<PipelineStage>) {
        result.subscribe((res: PipelineStage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PipelineStage) {
        this.eventManager.broadcast({ name: 'pipelineStageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPipelineById(index: number, item: Pipeline) {
        return item.id;
    }

    trackStageById(index: number, item: Stage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pipeline-stage-popup',
    template: ''
})
export class PipelineStagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pipelineStagePopupService: PipelineStagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pipelineStagePopupService
                    .open(PipelineStageDialogComponent as Component, params['id']);
            } else {
                this.pipelineStagePopupService
                    .open(PipelineStageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
