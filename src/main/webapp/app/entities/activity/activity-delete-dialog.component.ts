import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Activity } from './activity.model';
import { ActivityPopupService } from './activity-popup.service';
import { ActivityService } from './activity.service';

@Component({
    selector: 'jhi-activity-delete-dialog',
    templateUrl: './activity-delete-dialog.component.html'
})
export class ActivityDeleteDialogComponent {

    activity: Activity;

    constructor(
        private activityService: ActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.activityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'activityListModification',
                content: 'Deleted an activity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-activity-delete-popup',
    template: ''
})
export class ActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private activityPopupService: ActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.activityPopupService
                .open(ActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
