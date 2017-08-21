import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Activity } from './activity.model';
import { ActivityService } from './activity.service';

@Component({
    selector: 'jhi-activity-detail',
    templateUrl: './activity-detail.component.html'
})
export class ActivityDetailComponent implements OnInit, OnDestroy {

    activity: Activity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private activityService: ActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInActivities();
    }

    load(id) {
        this.activityService.find(id).subscribe((activity) => {
            this.activity = activity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'activityListModification',
            (response) => this.load(this.activity.id)
        );
    }
}
