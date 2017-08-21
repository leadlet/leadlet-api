import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmailTemplates } from './email-templates.model';
import { EmailTemplatesPopupService } from './email-templates-popup.service';
import { EmailTemplatesService } from './email-templates.service';

@Component({
    selector: 'jhi-email-templates-delete-dialog',
    templateUrl: './email-templates-delete-dialog.component.html'
})
export class EmailTemplatesDeleteDialogComponent {

    emailTemplates: EmailTemplates;

    constructor(
        private emailTemplatesService: EmailTemplatesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.emailTemplatesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'emailTemplatesListModification',
                content: 'Deleted an emailTemplates'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-email-templates-delete-popup',
    template: ''
})
export class EmailTemplatesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private emailTemplatesPopupService: EmailTemplatesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.emailTemplatesPopupService
                .open(EmailTemplatesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
