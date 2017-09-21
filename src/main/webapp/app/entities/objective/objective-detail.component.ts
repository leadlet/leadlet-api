import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Objective } from './objective.model';
import { ObjectiveService } from './objective.service';

@Component({
    selector: 'jhi-objective-detail',
    templateUrl: './objective-detail.component.html'
})
export class ObjectiveDetailComponent implements OnInit, OnDestroy {

    objective: Objective;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private objectiveService: ObjectiveService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInObjectives();
    }

    load(id) {
        this.objectiveService.find(id).subscribe((objective) => {
            this.objective = objective;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInObjectives() {
        this.eventSubscriber = this.eventManager.subscribe(
            'objectiveListModification',
            (response) => this.load(this.objective.id)
        );
    }
}
