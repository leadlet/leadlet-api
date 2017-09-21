import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { EmailTemplates } from './email-templates.model';
import { EmailTemplatesService } from './email-templates.service';

@Component({
    selector: 'jhi-email-templates-detail',
    templateUrl: './email-templates-detail.component.html'
})
export class EmailTemplatesDetailComponent implements OnInit, OnDestroy {

    emailTemplates: EmailTemplates;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private emailTemplatesService: EmailTemplatesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmailTemplates();
    }

    load(id) {
        this.emailTemplatesService.find(id).subscribe((emailTemplates) => {
            this.emailTemplates = emailTemplates;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmailTemplates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'emailTemplatesListModification',
            (response) => this.load(this.emailTemplates.id)
        );
    }
}
