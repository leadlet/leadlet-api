/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EmailTemplatesDetailComponent } from '../../../../../../main/webapp/app/entities/email-templates/email-templates-detail.component';
import { EmailTemplatesService } from '../../../../../../main/webapp/app/entities/email-templates/email-templates.service';
import { EmailTemplates } from '../../../../../../main/webapp/app/entities/email-templates/email-templates.model';

describe('Component Tests', () => {

    describe('EmailTemplates Management Detail Component', () => {
        let comp: EmailTemplatesDetailComponent;
        let fixture: ComponentFixture<EmailTemplatesDetailComponent>;
        let service: EmailTemplatesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [EmailTemplatesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EmailTemplatesService,
                    JhiEventManager
                ]
            }).overrideTemplate(EmailTemplatesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmailTemplatesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmailTemplatesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EmailTemplates(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.emailTemplates).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
