import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmailTemplates } from './email-templates.model';
import { EmailTemplatesPopupService } from './email-templates-popup.service';
import { EmailTemplatesService } from './email-templates.service';

@Component({
    selector: 'jhi-email-templates-dialog',
    templateUrl: './email-templates-dialog.component.html'
})
export class EmailTemplatesDialogComponent implements OnInit {

    emailTemplates: EmailTemplates;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private emailTemplatesService: EmailTemplatesService,
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
        if (this.emailTemplates.id !== undefined) {
            this.subscribeToSaveResponse(
                this.emailTemplatesService.update(this.emailTemplates));
        } else {
            this.subscribeToSaveResponse(
                this.emailTemplatesService.create(this.emailTemplates));
        }
    }

    private subscribeToSaveResponse(result: Observable<EmailTemplates>) {
        result.subscribe((res: EmailTemplates) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EmailTemplates) {
        this.eventManager.broadcast({ name: 'emailTemplatesListModification', content: 'OK'});
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
    selector: 'jhi-email-templates-popup',
    template: ''
})
export class EmailTemplatesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailTemplatesPopupService: EmailTemplatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.emailTemplatesPopupService
                    .open(EmailTemplatesDialogComponent as Component, params['id']);
            } else {
                this.emailTemplatesPopupService
                    .open(EmailTemplatesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
