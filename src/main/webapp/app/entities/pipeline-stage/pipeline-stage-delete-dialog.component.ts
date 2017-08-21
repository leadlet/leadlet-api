import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PipelineStage } from './pipeline-stage.model';
import { PipelineStagePopupService } from './pipeline-stage-popup.service';
import { PipelineStageService } from './pipeline-stage.service';

@Component({
    selector: 'jhi-pipeline-stage-delete-dialog',
    templateUrl: './pipeline-stage-delete-dialog.component.html'
})
export class PipelineStageDeleteDialogComponent {

    pipelineStage: PipelineStage;

    constructor(
        private pipelineStageService: PipelineStageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pipelineStageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pipelineStageListModification',
                content: 'Deleted an pipelineStage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pipeline-stage-delete-popup',
    template: ''
})
export class PipelineStageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pipelineStagePopupService: PipelineStagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pipelineStagePopupService
                .open(PipelineStageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
