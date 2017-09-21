import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Objective } from './objective.model';
import { ObjectivePopupService } from './objective-popup.service';
import { ObjectiveService } from './objective.service';

@Component({
    selector: 'jhi-objective-delete-dialog',
    templateUrl: './objective-delete-dialog.component.html'
})
export class ObjectiveDeleteDialogComponent {

    objective: Objective;

    constructor(
        private objectiveService: ObjectiveService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.objectiveService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'objectiveListModification',
                content: 'Deleted an objective'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-objective-delete-popup',
    template: ''
})
export class ObjectiveDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private objectivePopupService: ObjectivePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.objectivePopupService
                .open(ObjectiveDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
