/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LeadletApiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContactEmailDetailComponent } from '../../../../../../main/webapp/app/entities/contact-email/contact-email-detail.component';
import { ContactEmailService } from '../../../../../../main/webapp/app/entities/contact-email/contact-email.service';
import { ContactEmail } from '../../../../../../main/webapp/app/entities/contact-email/contact-email.model';

describe('Component Tests', () => {

    describe('ContactEmail Management Detail Component', () => {
        let comp: ContactEmailDetailComponent;
        let fixture: ComponentFixture<ContactEmailDetailComponent>;
        let service: ContactEmailService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LeadletApiTestModule],
                declarations: [ContactEmailDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContactEmailService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContactEmailDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContactEmailDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContactEmailService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContactEmail(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contactEmail).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
