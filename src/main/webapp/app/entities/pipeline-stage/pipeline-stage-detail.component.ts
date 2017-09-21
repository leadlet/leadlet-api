import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PipelineStage } from './pipeline-stage.model';
import { PipelineStageService } from './pipeline-stage.service';

@Component({
    selector: 'jhi-pipeline-stage-detail',
    templateUrl: './pipeline-stage-detail.component.html'
})
export class PipelineStageDetailComponent implements OnInit, OnDestroy {

    pipelineStage: PipelineStage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pipelineStageService: PipelineStageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPipelineStages();
    }

    load(id) {
        this.pipelineStageService.find(id).subscribe((pipelineStage) => {
            this.pipelineStage = pipelineStage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPipelineStages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pipelineStageListModification',
            (response) => this.load(this.pipelineStage.id)
        );
    }
}
