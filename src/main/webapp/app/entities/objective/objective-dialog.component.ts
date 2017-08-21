import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Objective } from './objective.model';
import { ObjectivePopupService } from './objective-popup.service';
import { ObjectiveService } from './objective.service';

@Component({
    selector: 'jhi-objective-dialog',
    templateUrl: './objective-dialog.component.html'
})
export class ObjectiveDialogComponent implements OnInit {

    objective: Objective;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private objectiveService: ObjectiveService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.objective.id !== undefined) {
            this.subscribeToSaveResponse(
                this.objectiveService.update(this.objective));
        } else {
            this.subscribeToSaveResponse(
                this.objectiveService.create(this.objective));
        }
    }

    private subscribeToSaveResponse(result: Observable<Objective>) {
        result.subscribe((res: Objective) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Objective) {
        this.eventManager.broadcast({ name: 'objectiveListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-objective-popup',
    template: ''
})
export class ObjectivePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private objectivePopupService: ObjectivePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.objectivePopupService
                    .open(ObjectiveDialogComponent as Component, params['id']);
            } else {
                this.objectivePopupService
                    .open(ObjectiveDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
