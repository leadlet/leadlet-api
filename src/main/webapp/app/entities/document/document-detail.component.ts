import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Document } from './document.model';
import { DocumentService } from './document.service';

@Component({
    selector: 'jhi-document-detail',
    templateUrl: './document-detail.component.html'
})
export class DocumentDetailComponent implements OnInit, OnDestroy {

    document: Document;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private documentService: DocumentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocuments();
    }

    load(id) {
        this.documentService.find(id).subscribe((document) => {
            this.document = document;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocuments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'documentListModification',
            (response) => this.load(this.document.id)
        );
    }
}
